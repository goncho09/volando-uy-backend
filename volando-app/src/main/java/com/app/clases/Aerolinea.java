package com.app.clases;

import com.app.datatypes.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Aerolinea extends Usuario {
    private String descripcion;
    private String linkWeb;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "aerolinea_rutas",
            joinColumns = @JoinColumn(name = "aerolinea_nombre"),
            inverseJoinColumns = @JoinColumn(name = "ruta_nombre")
    )
    private List<RutaDeVuelo> rutasDeVuelo;

    public Aerolinea() {}
    public Aerolinea(DtAerolinea aerolinea) {
        super(new DtUsuario(aerolinea.getNickname(), aerolinea.getNombre(), aerolinea.getEmail(), aerolinea.getPassword(), aerolinea.getUrlImage()));
        this.descripcion = aerolinea.getDescripcion();
        this.linkWeb = aerolinea.getLinkWeb();
        this.rutasDeVuelo = new ArrayList<>();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLinkWeb() {
        return linkWeb;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLinkWeb(String linkWeb) {
        this.linkWeb = linkWeb;
    }

    public List<RutaDeVuelo> getRutasDeVuelo() {
        return this.rutasDeVuelo;
    }

    public DtAerolinea getDatos(){
        return new DtAerolinea(this.getNickname(), this.getNombre(), this.getEmail(), this.getUrlImage(), this.getDescripcion(), this.getLinkWeb(), this.getRutasDeVuelo());
    }

    public void addRuta(RutaDeVuelo ruta) {
        this.rutasDeVuelo.add(ruta);
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public void mostrarDatos() {
        System.out.println("Datos: " + nickname + " - " + nombre + " - " +
                email + " - " + descripcion + " - " + linkWeb);
    }
}
