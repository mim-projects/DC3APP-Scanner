/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mim.mavenproject1.controller;

import com.jfoenix.controls.JFXSnackbar;
import com.mim.mavenproject1.application.Main;
import com.mim.mavenproject1.util.SerialPortDTO;
import com.mim.mavenproject1.util.interfaces.Command;
import com.mim.models.IngresoDTO;
import com.mim.models.Trabajador;
import com.mim.util.MasterView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author marcoisaacvazquezgutierrez
 */
public class ConfigurationController extends MasterView implements Initializable, Command {

    @FXML
    private StackPane mainStack;
    @FXML
    private Button backBtnMain;
    @FXML
    private ComboBox<SerialPortDTO> portsField;

    private OnViewInteractionListener mListener;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //backBtnMain.setStyle("-fx-background-image: url('arrow.png')");
        System.out.println("cargando imagen flecha.....");
        Image img = new Image(getClass().getClassLoader().getResourceAsStream("arrow.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(40);
        view.setPreserveRatio(true);

        //Attach image to the button
        backBtnMain.setGraphic(view);
        backBtnMain.setText("");
        //Set the image to the top
        backBtnMain.setContentDisplay(ContentDisplay.TOP);

    }

    @Override
    public void attach(Main main) {
        super.attach(main); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        mListener = (OnViewInteractionListener) main;
    }

    @FXML
    public void showMainLayout(MouseEvent event) {
        mListener.showMainLayout();
    }

    @FXML
    public void saveConfig(MouseEvent event) {
        if (portsField.getValue() == null) {
            showNotification("Selecciona puerto....", false);
            return;
        }
        if (mListener.openSelectedPort(portsField.getValue())) {
            showNotification("Puerto configurado con exito", true);
        } else {
            showNotification("Hubo algun error....", false);
        }

    }

    @Override
    public void buildContet(Pane content) {
        setUpUI();
    }

    @Override
    public void updateInfo(Trabajador trabajador, IngresoDTO ingreso) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void buildNavigation(Pane navigation) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void closeNavigationDrawer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void removeBlockUI() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void showNotification(String msg, boolean result) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/notificationMsg.fxml"));

                try {
                    StackPane root = loader.load();

                    Optional<Node> res = root.getChildren().stream().filter(e -> {
                        if (e.getAccessibleText().equals("snackText")) {
                            return true;
                        }
                        return false;
                    }).findFirst();
                    ((Text) res.get()).setText(msg);
                    if (result) {
                        root.setStyle("-fx-background-color: #00695c");
                    } else {
                        root.setStyle("-fx-background-color: RED");
                    }
                    /*StackPane root=new StackPane();
                root.setPrefWidth(600);
                root.setPrefHeight(144);
                root.setStyle("-fx-background-color: RED");
                Text msgText=new Text();
                msgText.setStyle("-fx-font-size: 24, -fx-fill: WHITE");
                msgText.setText(msg);
                root.getChildren().add(msgText);*/
                    JFXSnackbar bar = new JFXSnackbar(mainStack);
                    bar.enqueue(new JFXSnackbar.SnackbarEvent(root));
                    System.out.println("MOSTRAR ERROR.......!!!!!!");
                } catch (IOException ex) {
                    Logger.getLogger(LayoutController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
    }

    @Override
    public void changeBackGroundColr(String color) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setUpUI() {
        List<SerialPortDTO> res = mListener.retrievePortsNames();
        if (!res.isEmpty()) {
            for (SerialPortDTO re : res) {
                portsField.getItems().add(re);
            }
        }

        portsField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.out.println("selected: " + portsField.getValue());
            }

        });
    }

    public interface OnViewInteractionListener {

        public void showMainLayout();

        public List<SerialPortDTO> retrievePortsNames();

        public boolean openSelectedPort(SerialPortDTO port);
    }

}
