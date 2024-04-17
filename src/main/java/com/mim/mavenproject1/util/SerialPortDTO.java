/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mim.mavenproject1.util;

import java.io.Serializable;

/**
 *
 * @author marcoisaacvazquezgutierrez
 */
public class SerialPortDTO implements Serializable {

    private String portName;

    private String descriptionName;

    public SerialPortDTO(String portName, String descriptionName) {
        this.portName = portName;
        this.descriptionName = descriptionName;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    @Override
    public String toString() {

        return portName + " - " + descriptionName;
    }

}
