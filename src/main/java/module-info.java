module com.mim.mavenproject1 {
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.jfoenix;
    requires com.github.kwhat.jnativehook;
    requires com.google.gson;
    requires java.base;
    requires com.fazecast.jSerialComm;
    
   

    opens  com.mim.mavenproject1.application to com.google.gson , javafx.fxml ;
    opens com.mim.models to com.google.gson;
    exports com.mim.mavenproject1.application;
    
    opens  com.mim.mavenproject1.controller to javafx.fxml;
    exports com.mim.mavenproject1.controller;
}
