/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mim.mavenproject1.controller;

import com.mim.mavenproject1.application.Main;
import com.mim.models.IngresoDTO;
import com.mim.models.Trabajador;
import com.mim.util.MasterView;
import com.mim.mavenproject1.util.interfaces.Command;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author robb
 */
public class ProfileController extends MasterView implements Initializable, Command {

    @FXML
    private ImageView header;
    @FXML
    private AnchorPane anchorParent;

    @FXML
    private VBox card;
    @FXML
    private Text accesoField;

    @FXML
    private Text empresaField;

    @FXML
    private Text estatusField;

    @FXML
    private Text horarioField;

    @FXML
    private Text plantaField;

    @FXML
    private Text socialField;

    @FXML
    private Text nombreField;

    @FXML
    private Text areaField;

    @FXML
    private Text puestoField;

    @FXML
    private StackPane stackProfile;

    @FXML
    private StackPane backgroundParent;

    private Circle circleProfile;

    @FXML
    private AnchorPane genPane;

    @FXML
    private StackPane genStack;

    private OnViewInteractionListener mListener;
    private Image imgProfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //genStack.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));
        System.out.println("init profile ctrl");
        Image img = new Image(getClass().getClassLoader().getResourceAsStream("nav_menu_header_bg.png"));
        header.setImage(img);
        header.fitWidthProperty().bind(anchorParent.widthProperty());

        Image imgDefault = new Image(getClass().getClassLoader().getResourceAsStream("man.jpg"));

        circleProfile = new Circle();
        circleProfile.setRadius(120);
        circleProfile.setFill(new ImagePattern(imgDefault));
        circleProfile.setStroke(Color.BLACK);
        circleProfile.setStrokeWidth(2);

        stackProfile.getChildren().add(circleProfile);

        //backgroundParent.setStyle("-fx-background-color:GREEN");
    }

    @Override
    public void attach(Main main) {
        super.attach(main); //To change body of generated methods, choose Tools | Templates.
        this.mListener = (OnViewInteractionListener) main;
    }

    @Override
    public void buildContet(Pane content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateInfo(Trabajador trabajador, IngresoDTO ingreso) {

        if (ingreso == null) {
            return;
        }

        if (trabajador == null) {
            return;
        }

        if (ingreso.getResult() != null) {
            if (ingreso.getResult().equals("APROBADO")) {
                changeBackGroundColr("#00695c");
                mListener.turnOnGreenIndicator();
                mListener.turnOffRedIndicator();
            } else {
                changeBackGroundColr("RED");
                mListener.turnOnRedIndicator();
                mListener.turnOffGreenIndicator();
            }
        }

        imgProfile = new Image(Main.BASE + "com.mim.entities.trabajador/api/" + trabajador.getIdtrabajador());

        if (imgProfile.isError()) {
            imgProfile = new Image(getClass().getClassLoader().getResourceAsStream("man.jpg"));
        }
        FadeTransition ftOut = new FadeTransition(Duration.millis(500), circleProfile);
        ftOut.setFromValue(1);
        ftOut.setToValue(0);
        ftOut.play();

        ftOut.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                circleProfile.setFill(new ImagePattern(imgProfile));
                FadeTransition ft = new FadeTransition(Duration.millis(500), circleProfile);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
                ft.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                       if(mListener!=null){
                           mListener.resumeListener();
                       }
                    }

                });
            }

        });

        nombreField.setText(trabajador.getNombreCompleto());
        areaField.setText(trabajador.getAreaIdarea().getNombre());
        puestoField.setText(trabajador.getPuestoIdpuesto().getNombrePuesto());
        empresaField.setText(trabajador.getEmpresaIdempresa().getNombre());
        estatusField.setText(trabajador.getEstatus());
        accesoField.setText(ingreso.getResult());
        horarioField.setText(ingreso.getHorario());
        plantaField.setText(ingreso.getNombreZona());
        socialField.setText(trabajador.getNss());

    }

    @Override
    public void buildNavigation(Pane navigation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeNavigationDrawer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeBlockUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showNotification(String msg, boolean result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeBackGroundColr(String color) {
        if (backgroundParent != null) {
            backgroundParent.setStyle("-fx-background-color:" + color);
        }
    }

    public interface OnViewInteractionListener {

        public void turnOnIndicators();

        public void turnOffIndicators();

        public void turnOnGreenIndicator();

        public void turnOffGreenIndicator();

        public void turnOnRedIndicator();

        public void turnOffRedIndicator();

        public void retrieveSavedPort();

        public void resumeListener();
    }

}
