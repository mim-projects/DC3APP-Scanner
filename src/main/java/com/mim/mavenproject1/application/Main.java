/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mim.mavenproject1.application;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.google.gson.Gson;
import com.mim.mavenproject1.controller.ConfigurationController;
import com.mim.mavenproject1.controller.LayoutController;
import com.mim.mavenproject1.controller.LoginController;
import com.mim.mavenproject1.controller.NavigationMenuController;
import com.mim.mavenproject1.controller.ProfileController;
import com.mim.models.IngresoDTO;
import com.mim.models.Trabajador;
import com.mim.mavenproject1.util.GlobalKeyListenerExample;
import com.mim.util.MasterView;
//import com.mim.util.api.IngresoAPI;
//import com.mim.util.api.TrabajadorAPI;
import com.mim.mavenproject1.util.interfaces.Command;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.security.auth.spi.LoginModule;
import com.fazecast.jSerialComm.SerialPort;
import com.mim.mavenproject1.util.IndicatorWatcher;
import com.mim.mavenproject1.util.Singleton;
import com.mim.mavenproject1.util.Watcher;
import com.mim.mavenproject1.util.interfaces.WatcherCommand;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
//import retrofit2.Response;

/**
 *
 * @author robb
 */
public class Main extends Application implements ProfileController.OnViewInteractionListener,
        LayoutController.OnViewInteractionListener, NavigationMenuController.OnViewInteractionListener,
        IndicatorWatcher.WatcherListener, Watcher.WatcherListener, LoginController.OnViewInteractionListener,
        ConfigurationController.OnViewInteractionListener {

    public static final String LAYOUT = "Layout.fxml";
    public static final String PROFILE = "fxml/Profile.fxml";
    public static final String NAVIGATION = "NavigationMenu.fxml";
    public static final String LOGIN = "fxml/Login.fxml";
    public static final String CONFIG = "fxml/Configuration.fxml";

    public static final String BASE = "https://dc3-2022.ny-2.paas.massivegrid.net/dc3BackEnd2/webresources/";

    public static void main(String[] args) {
        launch(args);
    }

    private Command commandLayout;
    private Command loginLayout;
    private Command configLayout;
    private Command profileLayout;
    private Command navigationLayout;

    private WatcherCommand commandListener;
    private WatcherCommand commandIndicatorsListener;
    private String pendingNavigation = null;
    private Stage mainStage;
    Map<Integer, Object> layoutMap;
    private Map<Integer, Object> configMap;
    private Map<Integer, Object> navigationMap;
    private Map<Integer, Object> loginMap;
    private String keyResult;
    private SerialPort port;
    private List<SerialPort> portList = new ArrayList<>();

    private String matchName;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("DC3APP");

        layoutMap = setUpUI();

        mainStage = stage;
        //Scene scene = new Scene((Parent) layoutMap.get(2));
        Scene scene = new Scene((Parent) loginMap.get(2));
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            //System.exit(1);
        });
        stage.setScene(scene);
        stage.show();
        stage.toFront();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample(new GlobalKeyListenerExample.ReporterListener() {
            @Override
            public void launchWatcher() {
                System.out.println("Lanzando watcher.....");
                final Watcher watcher = new Watcher(Main.this);
                commandListener = (WatcherCommand) watcher;
                new Thread(watcher).start();
            }

            @Override
            public void validCodeResult() {
                keyResult = null;
                for (String str : Singleton.getInstance().getKeyList()) {
                    if (str != null) {
                        if (keyResult == null) {
                            keyResult = str;
                        } else {
                            keyResult = keyResult + str;
                        }
                    }
                }
                System.out.println("Codigo obetnido con exito....." + keyResult);
                Singleton.getInstance().clearList();
                Singleton.getInstance().setControl(false);
                if (keyResult != null) {
                    commandIndicatorsListener.restart();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showAboutPage(keyResult);
                        }

                    });

                }
            }

            @Override
            public void restartCount() {
                if (commandListener != null) {
                    commandListener.restart();
                }
            }

        }));

    }

    public Parent retrieveMainPage() {
        return (Parent) layoutMap.get(2);
    }

    @Override
    public void stop() throws Exception {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
        if (Singleton.getInstance().isControl()) {
            Singleton.getInstance().setControl(false);
        }

        if (Singleton.getInstance().isIndicatorControl()) {
            Singleton.getInstance().setIndicatorControl(false);
        }

        closePortSafely();
        System.out.println("adioooos......");
        System.exit(0);
    }

    private Map<Integer, Object> setUpUI() throws IOException {
        configMap = createView(CONFIG);
        configLayout = (Command) configMap.get(1);

        loginMap = createView(LOGIN);
        loginLayout = (Command) loginMap.get(1);
        Map<Integer, Object> layoutMap = createView(LAYOUT);
        commandLayout = (Command) layoutMap.get(1);
        Map<Integer, Object> profileMap = createView(PROFILE);
        profileLayout = (Command) profileMap.get(1);
        navigationMap = createView(NAVIGATION);
        navigationLayout = (Command) layoutMap.get(1);
        commandLayout.buildContet((Pane) profileMap.get(2));
        //commandLayout.buildContet((Pane) loginMap.get(2));
        commandLayout.buildNavigation((Pane) navigationMap.get(2));
        return layoutMap;
    }

    public Map<Integer, Object> createView(String name) {
        try {
            System.out.println("Name: " + name);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(name));
            Pane parent = loader.load();
            MasterView ctrl = loader.getController();
            ctrl.attach(this);

            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, ctrl);
            map.put(2, parent);
            parent.setMouseTransparent(false);
            System.out.println("si cargue fxml....");
            return map;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void showAboutPage(String codigo) {
        //System.out.println("Laura..... ");
        if (mainStage.isFocused()) {
            commandLayout.closeNavigationDrawer();
        }

        if (!Singleton.getInstance().isIndicatorControl()) {
            Singleton.getInstance().setIndicatorControl(true);
            Main.this.launchIndicatorWatcher();
        } else {
            commandIndicatorsListener.restart();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("hola crayola.....invocando servicio: " + codigo);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(BASE + "com.mim.entities.trabajador/credencialCaseta/" + codigo))
                        .build();

                try {
                    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                    int codigo = response.statusCode();
                    if (codigo == 500) {
                        if (mainStage.isFocused()) {
                            commandLayout.removeBlockUI();
                        }
                        commandLayout.showNotification("Error al recibir informacion....", false);
                        return;
                    }
                    System.out.println("codigo: " + codigo);
                    System.out.println("body: " + response.body());

                    Gson gson = new Gson();

                    Trabajador currentWorker = gson.fromJson(response.body(), Trabajador.class);
                    if (currentWorker != null) {
                        System.out.println("currentWorker: " + currentWorker.getNombreCompleto());

                        HttpClient client2 = HttpClient.newHttpClient();
                        HttpRequest request2 = HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create(BASE + "com.mim.entities.ingreso/ingresoCaseta/" + currentWorker.getIdtrabajador()))
                                .build();
                        HttpResponse<String> response2 = client2.send(request2, BodyHandlers.ofString());

                        System.out.println("bodyAcceso: " + response2.body());
                        IngresoDTO ingresoRes = gson.fromJson(response2.body(), IngresoDTO.class);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (mainStage.isFocused()) {
                                    commandLayout.removeBlockUI();
                                } else {
                                    //mainStage.show();
                                }
                                profileLayout.updateInfo(currentWorker, ingresoRes);
                            }

                        });
                    }

                } catch (IOException ex) {
                    if (mainStage.isFocused()) {
                        commandLayout.removeBlockUI();
                    }
                    commandLayout.showNotification("Error al recibir informacion....", false);

                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    if (mainStage.isFocused()) {
                        commandLayout.removeBlockUI();
                    }
                    commandLayout.showNotification("Error al recibir informacion....", false);

                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }).start();

    }

    private void checkUsbPorts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("checando usb ports....");
                SerialPort[] ports = SerialPort.getCommPorts();

                portList.clear();
                for (SerialPort port : ports) {
                    System.out.println(port.getSystemPortName());
                    portList.add(port);

                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //perform navigation.....
                        if (configLayout != null) {
                            configLayout.buildContet(null);
                        }
                        placePendingNavigation(Main.CONFIG);
                    }
                });

            }
        }).start();

    }

    public boolean openSelectedPort(String portName) {
        try {
            System.out.println("Vamos a intentar abir el puerto: " + portName);
            port = SerialPort.getCommPort(portName);
            saveSelectedPort(portName);
            if (port.openPort()) {
                System.out.println("SI SE ABRIO EL PUERTO");
                configPuerto();
                //turnOffGreenLight();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void timedOut() {
        System.out.println("borrando lista y detenindo watcher");
        Singleton.getInstance().setControl(false);
        Singleton.getInstance().clearList();

    }

    @Override
    public void showMainLayout() {

        //Scene scene = new Scene(retrieveMainPage());
        //mainStage.setScene(scene);
        mainStage.getScene().setRoot(retrieveMainPage());
        retrieveSavedPort();
    }

    public void showPage(Pane pane) {
        mainStage.getScene().setRoot(pane);
    }

    @Override
    public void placePendingNavigation(String navigation) {
        if (commandLayout != null) {
            commandLayout.closeNavigationDrawer();
            commandLayout.removeBlockUI();
            //commandLayout.placeBurguerIcon();
            //commandLayout.buildNavigation((Pane) navigationMap.get(2));
        }
        pendingNavigation = navigation;
    }

    @Override
    public String isTherePendingNavigation() {
        return pendingNavigation;
    }

    @Override
    public void performNavigation() {
        switch (pendingNavigation) {
            case CONFIG:
                pendingNavigation = null;
                showPage((Pane) configMap.get(2));
                break;
            case LOGIN:
                pendingNavigation = null;
                showPage((Pane) loginMap.get(2));
                break;
        }
    }

    @Override
    public boolean attemptLogin(String user, String password) {
        try {
            /* Thread.sleep(3000);
            if (user == null) {
                return false;
            }
            if (password == null) {
                return false;
            }
            if (user.equals("cristina")) {
                if (password.equals("safety")) {
                    return true;
                }
            }*/

            if (password == null) {
                return false;
            }

            if (user == null) {
                return false;
            }

            HttpClient client2 = HttpClient.newHttpClient();
            HttpRequest request2 = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(BASE + "com.mim.entities.usuario/login/" + user + "/" + password))
                    .build();
            HttpResponse<String> response2 = client2.send(request2, BodyHandlers.ofString());
            int code = response2.statusCode();
            System.out.println("codigo: " + response2.statusCode());
            //System.out.println("result: " + response2.body());
            if (code != 200) {
                return false;
            } else {
                return true;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void turnOnGreenLight() {
        if (port == null) {
            return;
        }
        if (!port.isOpen()) {
            return;
        }

        System.out.println("enviando comando.....");
        byte[] write = new byte[4];
        write[0] = (byte) 0xA0;
        write[1] = (byte) 0x01;
        write[2] = (byte) 0x01;
        write[3] = (byte) 0xA2;
        int res = port.writeBytes(write, 4);
        System.out.println("resultado: " + res);

    }

    private void turnOffGreenLight() {
        if (port == null) {
            return;
        }
        if (!port.isOpen()) {
            return;
        }

        System.out.println("enviando comando.....");
        byte[] write = new byte[4];
        write[0] = (byte) 0xA0;
        write[1] = (byte) 0x01;
        write[2] = (byte) 0x00;
        write[3] = (byte) 0xA1;
        int res = port.writeBytes(write, 4);
        System.out.println("resultado: " + res);

    }

    private void turnOnRedLight() {
        if (port == null) {
            return;
        }
        if (!port.isOpen()) {
            return;
        }

        System.out.println("enviando comando.....");
        byte[] write = new byte[4];
        write[0] = (byte) 0xA0;
        write[1] = (byte) 0x02;
        write[2] = (byte) 0x01;
        write[3] = (byte) 0xA3;
        int res = port.writeBytes(write, 4);
        System.out.println("resultado: " + res);

    }

    private void turnOffRedLight() {
        if (port == null) {
            return;
        }
        if (!port.isOpen()) {
            return;
        }

        System.out.println("enviando comando.....");
        byte[] write = new byte[4];
        write[0] = (byte) 0xA0;
        write[1] = (byte) 0x02;
        write[2] = (byte) 0x00;
        write[3] = (byte) 0xA2;
        int res = port.writeBytes(write, 4);
        System.out.println("resultado: " + res);

    }

    private void configPuerto() {
        int baudRate = 9600;
        int dataBits = 8;
        int stopBits = SerialPort.ONE_STOP_BIT;
        int parity = SerialPort.NO_PARITY;

        port.setComPortParameters(baudRate, dataBits, stopBits, parity);
    }

    private void closePortSafely() {
        if (port == null) {
            return;
        }
        if (!port.isOpen()) {
            return;
        }
        port.closePort();
    }

    @Override
    public void turnOnIndicators() {
        turnOnGreenLight();

        turnOnRedLight();
    }

    @Override
    public void turnOffIndicators() {
        turnOffGreenLight();

        turnOffRedLight();
    }

    @Override
    public void turnOnGreenIndicator() {

        turnOnGreenLight();
    }

    @Override
    public void turnOffGreenIndicator() {

        turnOffGreenLight();
    }

    @Override
    public void turnOnRedIndicator() {

        turnOnRedLight();
    }

    @Override
    public void turnOffRedIndicator() {

        turnOffRedLight();
    }

    @Override
    public void loadUsbPorts() {
        checkUsbPorts();
    }

    @Override
    public List<String> retrievePortsNames() {
        if (portList == null) {
            return new ArrayList<>();
        }
        if (portList.isEmpty()) {
            //List<String> temp = new ArrayList<>();
            //temp.add("test");
            return new ArrayList<>();
            //return temp;
        }

        List<String> names = new ArrayList<>();

        System.out.println("ports list names size: " + portList.size());
        for (SerialPort serialPort : portList) {
            names.add(serialPort.getSystemPortName());
        }
        return names;
    }

    private void saveSelectedPort(String portName) {
        try {
            File fil = new File("config.txt");
            if (!fil.exists()) {
                if (fil.createNewFile()) {
                    System.out.println("archivo creado.....");
                }
            }

            FileOutputStream out = new FileOutputStream(fil);
            try (PrintStream writer = new PrintStream(out, true, StandardCharsets.UTF_8)) {
                writer.print(portName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retrieveSavedPort() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File fil = new File("config.txt");
                    if (!fil.exists()) {
                        if (fil.createNewFile()) {
                            System.out.println("archivo creado.....");
                        }
                    }

                    StringBuilder sb = new StringBuilder();
                    byte[] buffer = new byte[10];
                    try (FileInputStream in = new FileInputStream(fil)) {
                        int content;
                        // reads a byte at a time, if it reached end of the file, returns -1
                        while ((content = in.read(buffer)) != -1) {

                            sb.append(new String(buffer));
                            buffer = new byte[10];
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("puerto guardado: " + sb.toString() + " size: " + sb.toString().length());
                    char[] chars = sb.toString().toCharArray();
                    StringBuilder res = new StringBuilder();
                    for (char aChar : chars) {
                        System.out.println("val: " + aChar + " int: " + (int) aChar);
                        if ((int) aChar != 0) {
                            res.append(aChar);
                        }
                    }

                    System.out.println("Final result: " + res.toString() + " size: " + res.toString().length());
                    if (!res.toString().isBlank()) {
                        if (!openSelectedPort(res.toString())) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("NO SE ABRIO EL PUERTO....... FALLAAAAAA.....");
                                    commandLayout.showNotification("Error al abrir puerto....", false);
                                }

                            });
                        }
                    } else {
                        System.out.println("entre aqui..... port ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public void timedOutIndicators() {
        System.out.println("hay que apagar los indicadores....");
        turnOffIndicators();
        Singleton.getInstance().setIndicatorControl(false);
    }

    private void launchIndicatorWatcher() {
        System.out.println("Lanzando watcher..... INDICADORES ");
        final IndicatorWatcher watcher = new IndicatorWatcher(Main.this);
        commandIndicatorsListener = (WatcherCommand) watcher;
        new Thread(watcher).start();
    }

}
