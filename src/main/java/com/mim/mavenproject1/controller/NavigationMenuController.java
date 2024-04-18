/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mim.mavenproject1.controller;

import com.jfoenix.controls.JFXButton;
import com.mim.mavenproject1.application.Main;
import com.mim.util.MasterView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author robb
 */
public class NavigationMenuController extends MasterView implements Initializable {

    private Circle circulo;

    @FXML
    private JFXButton aboutButton;

    @FXML
    private JFXButton profileButton;

    @FXML
    private StackPane stack;

    private OnViewInteractionListener mListener;

    private boolean control = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        circulo = new Circle();
        circulo.setRadius(100);
        Image mg = new Image(getClass().getClassLoader().getResourceAsStream("prueba_main.jpg"));
        circulo.setFill(new ImagePattern(mg));
        circulo.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLUEVIOLET));

        stack.getChildren().add(circulo);
    }

    @Override
    public void attach(Main main) {
        super.attach(main); //To change body of generated methods, choose Tools | Templates.
        this.mListener = main;
    }

    @FXML
    public void showAboutView(MouseEvent event) {// 855563013 no aprobado
        //mListener.showAboutPage("920685057");
        if (!control) {
            control = true;
            mListener.showAboutPage("846580521");
            //mListener.turnOnIndicators();
        } else {
            control = false;
            mListener.showAboutPage("479462761");
            //mListener.turnOffIndicators();
        }
    }

    @FXML
    public void showSignOutView(MouseEvent event) {
        mListener.placePendingNavigation(Main.LOGIN);
    }

    @FXML
    public void showConfigView(MouseEvent event) {
        //mListener.showConfigPage();
        //mListener.placePendingNavigation(Main.CONFIG);
        mListener.loadUsbPorts();
    }

    public interface OnViewInteractionListener {

        public void loadUsbPorts();

        public void showAboutPage(String codigo);

        // public void showConfigPage();
        public void placePendingNavigation(String navigation);

        public void turnOnIndicators();

        public void turnOffIndicators();

        public void turnOnGreenIndicator();

        public void turnOffGreenIndicator();

        public void turnOnRedIndicator();

        public void turnOffRedIndicator();
    }

}
