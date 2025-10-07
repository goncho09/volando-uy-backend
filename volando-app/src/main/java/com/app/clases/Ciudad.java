package com.app.clases;

import com.app.datatypes.DtCiudad;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
// @Table(name="ciudad") <- Se toma por defecto el nombre de la clase "Ciudad"
public class Ciudad {
    @EmbeddedId
    private CiudadId id;

    private String aeropuerto;
    private String descripcion;
    private String sitioWeb;
    private LocalDate fechaAlta;

    public Ciudad() {};

    public Ciudad(DtCiudad ciudad) {
        this.aeropuerto = ciudad.getAeropuerto();
        this.descripcion = ciudad.getDescripcion();
        this.sitioWeb = ciudad.getSitioWeb();
        this.fechaAlta = ciudad.getFechaAlta();
        this.id = new CiudadId(ciudad.getNombre(), ciudad.getPais());
    }

    public CiudadId getId() {
        return id;
    }

    public void setId(CiudadId id) {this.id = id;}

    public String listarId() {
        return id.toString();
    }

    public String getNombre() {
        return id.getNombre();
    }

    public void setNombre(String nombre) {
        this.id.setNombre(nombre);
    }

    public String getPais() {
        return id.getPais();
    }

    public void setPais(String ciudad) {
        this.id.setPais(ciudad);
    }

    public String getAeropuerto() {
        return aeropuerto;
    }

    public void setAeropuerto(String aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public DtCiudad getDatos(){
        return new DtCiudad(this.getNombre(), this.getPais(), this.getAeropuerto(), this.getDescripcion(), this.getSitioWeb(), this.getFechaAlta());
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
