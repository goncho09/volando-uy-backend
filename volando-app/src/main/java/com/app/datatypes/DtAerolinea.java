package com.app.datatypes;

import com.app.clases.RutaDeVuelo;

import java.util.ArrayList;
import java.util.List;

public class DtAerolinea extends DtUsuario {
    private String descripcion;
    private String linkWeb;
    private List<RutaDeVuelo> rutasDeVuelo;

    public DtAerolinea() {}

    public DtAerolinea(String nickname, String nombre, String email, String password, String urlImage, String descripcion) {
        super(nickname, nombre, email, password, urlImage);
        this.descripcion = descripcion;
        this.linkWeb = "";
        this.rutasDeVuelo = new ArrayList<>();
    }

    public DtAerolinea(String nickname, String nombre, String email, String password, String urlImage, String descripcion, String linkWeb) {
        super(nickname, nombre, email, password, urlImage);
        this.descripcion = descripcion;
        this.linkWeb = linkWeb;
        this.rutasDeVuelo = new ArrayList<>();
    }

    public DtAerolinea(DtUsuario usuario, String descripcion, String linkWeb) {
        super(usuario.getNickname(), usuario.getNombre(), usuario.getEmail(), usuario.getPassword(), usuario.getUrlImage());
        this.descripcion = descripcion;
        this.linkWeb = linkWeb;
        this.rutasDeVuelo = new ArrayList<>();
    }

    public DtAerolinea(String nickname, String nombre, String email, String password, String urlImage, String descripcion, String linkWeb,List<RutaDeVuelo> rutasDeVuelo) {
        super(nickname, nombre, email, password, urlImage);
        this.descripcion = descripcion;
        this.linkWeb = linkWeb;
        this.rutasDeVuelo = rutasDeVuelo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getLinkWeb() {
        return this.linkWeb;
    }

    public List<RutaDeVuelo> getRutasDeVuelo() {
        return rutasDeVuelo;
    }

    public List<DtRuta> listarRutasDeVuelo() {
        List<DtRuta> listaRutas = new ArrayList<>();
        for( RutaDeVuelo r : this.getRutasDeVuelo()){
            listaRutas.add(r.getDatos());
        }
        return listaRutas;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLinkWeb(String linkWeb) {
        this.linkWeb = linkWeb;
    }

    public void setRutasDeVuelo(List<RutaDeVuelo> rutasDeVuelo) {
        this.rutasDeVuelo = rutasDeVuelo;
    }

    public void mostrarDatos(){ System.out.println("Datos de la aerolinea: " + this.getNickname() + " - " + this.getNombre() + " - " + this.getEmail() + " - " + this.getDescripcion() + " - " + this.getLinkWeb());}

}
