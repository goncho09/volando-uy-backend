package com.app.datatypes;

import com.app.clases.RutaDeVuelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class DtVuelo {
    private String nombre;
    private LocalDate fecha;
    private LocalTime duracion;
    private int maxTuristas;
    private int maxEjecutivos;
    private String urlImage;
    private LocalDate fechaAlta;
    private RutaDeVuelo rutaDeVuelo;
    private int cantReservas;

    public DtVuelo(){};

    public DtVuelo(String nombre, LocalDate fecha, LocalTime duracion, int maxTuristas, int maxEjecutivos, String urlImage,LocalDate fechaAlta,RutaDeVuelo rutaDeVuelo, int cantReservas) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.duracion = duracion;
        this.maxTuristas = maxTuristas;
        this.maxEjecutivos = maxEjecutivos;
        this.urlImage = urlImage;
        this.fechaAlta = fechaAlta;
        this.rutaDeVuelo = rutaDeVuelo;
        this.cantReservas = cantReservas;
    }

    public DtVuelo getDatos() {
        return new DtVuelo(this.nombre,this.fecha,this.duracion,this.maxTuristas,this.maxEjecutivos,this.urlImage,this.fechaAlta,this.rutaDeVuelo, this.cantReservas);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public int getMaxTuristas() {
        return maxTuristas;
    }

    public void setMaxTuristas(int maxTuristas) {
        this.maxTuristas = maxTuristas;
    }

    public int getMaxEjecutivos() {
        return maxEjecutivos;
    }

    public void setMaxEjecutivos(int maxEjecutivos) {
        this.maxEjecutivos = maxEjecutivos;
    }

    public String getUrlImage() {return this.urlImage;};

    public void setUrlImage(String urlImage) {this.urlImage = urlImage;}

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getCantReservas(){
        return this.cantReservas;
    }

    public RutaDeVuelo getRutaDeVuelo() {
        return rutaDeVuelo;
    }

    public void setRutaDeVuelo(RutaDeVuelo rutaDeVuelo) {
        this.rutaDeVuelo = rutaDeVuelo;
    }

    @Override
    public String toString() {return this.nombre;}
}
