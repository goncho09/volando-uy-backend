package com.app.datatypes;

import com.app.clases.RutaDeVuelo;
import com.app.clases.RutaEnPaquete;


import java.util.ArrayList;
import java.util.List;

public class DtPaquete {
    private String nombre;
    private String descripcion;
    private int validezDias;
    private float descuento;
    private float costo;
    private List<RutaEnPaquete> rutaEnPaquete;

    public  DtPaquete(){};

    public DtPaquete(String nombre, String descripcion, int validezDias, float descuento, float costo, List<RutaEnPaquete> rutaEnPaquete) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.validezDias = validezDias;
        this.descuento = descuento;
        this.costo = costo;
        this.rutaEnPaquete = rutaEnPaquete;
    }

    public DtPaquete(String nombre, String descripcion, int validezDias, float descuento, float costo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.validezDias = validezDias;
        this.descuento = descuento;
        this.costo = costo;
        this.rutaEnPaquete = new ArrayList<>();
    }

    public RutaDeVuelo getRutaDeVuelo(String nombre){
        for (RutaEnPaquete rp : this.rutaEnPaquete) {
            if (rp.getRutaDeVuelo().getNombre().equals(nombre)){
                return  rp.getRutaDeVuelo();
            }
        }
        return  null;
    }


    public List<RutaEnPaquete> getRutaEnPaquete() {
        return rutaEnPaquete;
    }

    public void setRutaEnPaquete(List<RutaEnPaquete> rutaEnPaquete) {
        this.rutaEnPaquete = rutaEnPaquete;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public int getValidezDias() {
        return validezDias;
    }

    public void setValidezDias(int validezDias) {
        this.validezDias = validezDias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() { return this.nombre; }
}
