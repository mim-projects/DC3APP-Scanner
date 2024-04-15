/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mim.mavenproject1.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robb
 */
public class Singleton {

    // Static variable reference of single_instance
    // of type Singleton
    private static Singleton single_instance = null;

    // Declaring a variable of type String
    private List<String> keyList = new ArrayList<>();
    private volatile boolean control = false;// para ver que whatcher siga corriendo. e identificar si estamos validando
    //private volatile boolean validating=false;// 

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private volatile boolean indicatorControl = false;

    private Singleton() {

    }

    // Static method
    // Static method to create instance of Singleton class
    public static Singleton getInstance() {
        if (single_instance == null) {
            single_instance = new Singleton();
        }

        return single_instance;
    }

    public synchronized List<String> getKeyList() {
        return keyList;
    }

    public synchronized void addElement(String element) {
        keyList.add(element);
    }

    public synchronized void clearList() {
        keyList.clear();
    }

    public synchronized boolean isControl() {
        return control;
    }

    public synchronized void setControl(boolean control) {
        this.control = control;
    }

    /*public synchronized boolean isValidating() {
        return validating;
    }

    public synchronized void setValidating(boolean validating) {
        this.validating = validating;
    }*/
    public boolean isIndicatorControl() {
        return indicatorControl;
    }

    public void setIndicatorControl(boolean indicatorControl) {
        this.indicatorControl = indicatorControl;
    }

}
