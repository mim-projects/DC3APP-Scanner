/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mim.mavenproject1.controller;

import com.jfoenix.controls.JFXSnackbar;
import com.mim.mavenproject1.application.Main;
import com.mim.mavenproject1.util.interfaces.Command;
import com.mim.models.IngresoDTO;
import com.mim.models.Trabajador;
import com.mim.util.MasterView;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author marcoisaacvazquezgutierrez
 */
public class LoginController extends MasterView implements Initializable, Command {
    
    @FXML
    private Circle circuloLogin;
    @FXML
    private StackPane mainStack;
    @FXML
    private Button loginButton;
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private AnchorPane parent;
    
    private OnViewInteractionListener mListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("me inicializce");
        if (loginButton == null) {
            System.out.println("boton nulo");
        }
        Image imgDefault = new Image(getClass().getClassLoader().getResourceAsStream("safety.jpg"));
        
        circuloLogin.setFill(new ImagePattern(imgDefault));
        circuloLogin.setStroke(Color.BLACK);
        circuloLogin.setStrokeWidth(2);
        Platform.runLater(() -> mainStack.requestFocus());
        
    }
    
    @Override
    public void attach(Main main) {
        super.attach(main); //To change body of generated methods, choose Tools | Templates.
        mListener = (OnViewInteractionListener) main;
        //System.out.println("Holaaaaa!!!!!");
    }
    
    @Override
    public void buildContet(Pane content) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
                    
                    JFXSnackbar bar = new JFXSnackbar(mainStack);
                    bar.enqueue(new JFXSnackbar.SnackbarEvent(root));
                    System.out.println("MOSTRAR ERROR.......!!!!!!");
                } catch (IOException ex) {
                    Logger.getLogger(LayoutController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        });
    }
    
    @FXML
    public void performLogin(MouseEvent event) {
        System.out.println("hola soy nuevo.....");
        //mListener.showMainLayout();
        showNotification("Espera un momento....", true);
        String userEntered = userField.getText();
        String passwordEntered = passwordField.getText();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mListener.attemptLogin(userEntered, passwordEntered)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            userField.setText(null);
                            passwordField.setText(null);
                            mListener.showMainLayout();
                        }
                        
                    });
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showNotification("Error al iniciar sesion....", false);
                        }
                        
                    });
                    
                }
            }
        }).start();
        
    }

    @Override
    public void changeBackGroundColr(String color) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public interface OnViewInteractionListener {
        
        public void showMainLayout();
        
        public boolean attemptLogin(String user, String password);
        
        public void retrieveSavedPort();
    }
}
