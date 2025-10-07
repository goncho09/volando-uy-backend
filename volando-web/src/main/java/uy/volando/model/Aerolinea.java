package uy.volando.model;

import java.util.ArrayList;
import java.util.List;

public class Aerolinea extends Usuario{
    private String descripcion;
    private String linkWeb;
    private List<RutaDeVuelo> rutasDeVuelo;


    public Aerolinea(String nickname, String nombre, String email, String password, String urlImage, String descripcion) {
        super(nickname, nombre, email, password, urlImage);
        this.descripcion = descripcion;
        this.linkWeb = "";
        this.rutasDeVuelo = new ArrayList<>();
    }

    public Aerolinea(String nickname, String nombre, String email, String password, String urlImage, String descripcion, String linkWeb) {
        super(nickname, nombre, email, password, urlImage);
        this.descripcion = descripcion;
        this.linkWeb = linkWeb;
        this.rutasDeVuelo = new ArrayList<>();
    }

    public Aerolinea(String nickname, String nombre, String email, String urlImage, String descripcion, String linkweb, List<RutaDeVuelo> rutasDeVuelo) {
        super(nickname, nombre, email, urlImage);
        this.descripcion = descripcion;
        this.linkWeb = linkweb;
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

//    public List<RutaDeVuelo> listarRutasDeVuelo() {
//        List<RutaDeVuelo> listaRutas = new ArrayList<>();
//        for( RutaDeVuelo r : this.getRutasDeVuelo()){
//            listaRutas.add(r.getDatos());
//        }
//        return listaRutas;
//    }

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
