/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mim.mavenproject1.util.interfaces;

import com.mim.models.IngresoDTO;
import com.mim.models.Trabajador;
import javafx.scene.layout.Pane;

/**
 *
 * @author robb
 */
public interface Command {

    public void buildContet(Pane content);
    
    public void updateInfo(Trabajador trabajador,IngresoDTO ingreso);
    
    public void buildNavigation(Pane navigation);
    
    public void closeNavigationDrawer();
    
    public void removeBlockUI();
    
    public void showNotification(String msg,boolean result);
    
    public void changeBackGroundColr(String color);
    
  
}
