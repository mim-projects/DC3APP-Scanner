/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mim.models;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author marcoisaac
 */
public class MapaCertificaciones implements Serializable {

    private static final long serialVersionUID = 1L;



    private Integer duracion;

    private Date fechaEjecucion;

    private String areaTematica;
    private String capacitador;
    private Integer dias;

    private String registro;
    private String tipo;

    private Certificaciones certificaciones;


    private Trabajador trabajadorIdtrabajador;

    public MapaCertificaciones() {
    }


    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }

    public String getCapacitador() {
        return capacitador;
    }

    public void setCapacitador(String capacitador) {
        this.capacitador = capacitador;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Certificaciones getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(Certificaciones certificaciones) {
        this.certificaciones = certificaciones;
    }


    public Trabajador getTrabajadorIdtrabajador() {
        return trabajadorIdtrabajador;
    }

    public void setTrabajadorIdtrabajador(Trabajador trabajadorIdtrabajador) {
        this.trabajadorIdtrabajador = trabajadorIdtrabajador;
    }

    public Integer getDias() {
        return this.dias;
    }

    public void setDias(Integer num) {
        this.dias = num;
    }
}
