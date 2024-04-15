/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mim.mavenproject1.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.mim.mavenproject1.application.Main;
import com.mim.models.IngresoDTO;
import com.mim.models.Trabajador;
import com.mim.util.MasterView;
import com.mim.mavenproject1.util.interfaces.Command;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author robb
 */
public class LayoutController extends MasterView implements Initializable, Command {

    @FXML
    private JFXHamburger burger;

    @FXML
    private JFXDrawer navigation;

    private Main context;

    @FXML
    private AnchorPane main_content;

    private OnViewInteractionListener mListener;

    @FXML
    private StackPane mainStack;
    private HamburgerBackArrowBasicTransition transition;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println("primero.....");

    }

    @Override
    public void attach(Main main) {
        super.attach(main); //To change body of generated methods, choose Tools | Templates.
        mListener = (OnViewInteractionListener) main;
        //System.out.println("Holaaaaa!!!!!");
    }

    @Override
    public void buildContet(Pane content) {
        final ObservableList<Node> children = main_content.getChildren();
        main_content.setMouseTransparent(false);
        main_content.mouseTransparentProperty().set(false);
        main_content.mouseTransparentProperty().setValue(false);
        if (children.size() > 0) {
            children.clear();
        }
        children.add(content);
    }

    @Override
    public void updateInfo(Trabajador trabajador, IngresoDTO ingreso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildNavigation(Pane nav) {

        //box = FXMLLoader.load(getClass().getClassLoader().getResource("NavigationMenu.fxml"));
        nav.prefHeightProperty().bind(navigation.prefHeightProperty());
        //AnchorPane profileContent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Profile.fxml"));

        //main_content.getChildren().add(profileContent);
        navigation.setSidePane(nav);

        transition = new HamburgerBackArrowBasicTransition(burger);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.out.println("termine animacion.....");
                if (navigation.isClosing()) {
                    System.out.println("EJECUTA ACCION.....!!!!");
                    if(mListener.isTherePendingNavigation()!=null){
                        mListener.performNavigation();
                    }
                }
            }

        });
        transition.setRate(-1);
        burger.getStyleClass().add("jfx-hamburger-icon");
        burger.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                transition.setRate(transition.getRate() * -1);
                transition.play();
                if (navigation.isOpened()) {
                    navigation.close();
                } else {
                    navigation.open();
                }
            }

        });

    }

    @Override
    public void closeNavigationDrawer() {
        if (navigation.isOpened()) {
            navigation.close();
            transition.setRate(transition.getRate() * -1);
            transition.play();
        }

        AnchorPane blockUI = new AnchorPane();
        blockUI.setAccessibleText("block");
        blockUI.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5)");
        mainStack.getChildren().add(blockUI);
        FadeTransition ft = new FadeTransition(Duration.millis(700), blockUI);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    @Override
    public void removeBlockUI() {
        List<Node> block = mainStack.getChildren().stream()
                .filter(e -> {
                    if (e.getAccessibleText() == null) {
                        return false;
                    }
                    if (e.getAccessibleText().equals("block")) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

        for (Node node : block) {
            FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    mainStack.getChildren().remove(node);
                }

            });
        }

    }

    @Override
    public void showNotification(String msg,boolean result) {
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
                    /*StackPane root=new StackPane();
                root.setPrefWidth(600);
                root.setPrefHeight(144);
                root.setStyle("-fx-background-color: RED");
                Text msgText=new Text();
                msgText.setStyle("-fx-font-size: 24, -fx-fill: WHITE");
                msgText.setText(msg);
                root.getChildren().add(msgText);*/
                    JFXSnackbar bar = new JFXSnackbar(mainStack);
                    bar.enqueue(new SnackbarEvent(root));
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

    public interface OnViewInteractionListener {
       public String isTherePendingNavigation();
       public void performNavigation();
    }

}
