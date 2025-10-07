package com.app.clases;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CiudadId implements Serializable {

    @Column(name="nombreCiudad")
    private String nombre;

    @Column(name="paisCiudad")
    private String pais;

    public CiudadId() {}

    public CiudadId(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CiudadId ciudadId = (CiudadId) o;
        return Objects.equals(nombre, ciudadId.nombre) && Objects.equals(pais, ciudadId.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, pais);
    }

    @Override
    public String toString() {
        return pais + ", " + nombre;
    }
}