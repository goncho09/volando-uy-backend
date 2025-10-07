package com.app.clases;

import com.app.datatypes.DtPaquete;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "paquete")
public class Paquete {
    @Id
    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private int validezDias;

    @Column(nullable = false)
    private float descuento;

    @Column(nullable = false)
    private float costo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="paquete_name")
    private List<RutaEnPaquete> rutaEnPaquete = new ArrayList<>();

    public Paquete() {}
    public Paquete(DtPaquete paquete) {
        this.nombre = paquete.getNombre();
        this.descripcion = paquete.getDescripcion();
        this.validezDias = paquete.getValidezDias();
        this.descuento = paquete.getDescuento();
        this.costo = paquete.getCosto();
        this.rutaEnPaquete = paquete.getRutaEnPaquete();
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

    public DtPaquete getDatos(){
        return new DtPaquete(this.getNombre(),this.getDescripcion(),this.getValidezDias(),this.getDescuento(),this.getCosto(),this.getRutaEnPaquete());
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
