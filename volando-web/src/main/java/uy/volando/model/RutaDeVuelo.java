package uy.volando.model;

import com.app.datatypes.DtCategoria;
import com.app.datatypes.DtRuta;
import uy.volando.enums.EstadoRuta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RutaDeVuelo {
    private String nombre;
    private String descripcion;
    private LocalTime duracion;
    private float costoTurista;
    private float costoEjecutivo;
    private float equipajeExtra;
    private LocalDate fechaAlta;
    private String urlImagen;
    private EstadoRuta estado;
    private List<Categoria> categorias;
    private Ciudad ciudadOrigen;
    private Ciudad ciudadDestino;

    public RutaDeVuelo(String nombre, String descripcion, LocalTime duracion, float costoTurista, float costoEjecutivo, float equipajeExtra, LocalDate fechaAlta, String urlImagen, EstadoRuta estado, List<Categoria> categorias, Ciudad origen, Ciudad destino) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.costoTurista = costoTurista;
        this.costoEjecutivo = costoEjecutivo;
        this.equipajeExtra = equipajeExtra;
        this.fechaAlta = fechaAlta;
        this.urlImagen = urlImagen;
        this.estado = estado;
        this.categorias = categorias;
        this.ciudadOrigen = origen;
        this.ciudadDestino = destino;
    }

    public RutaDeVuelo(DtRuta dtRuta) {
        this.nombre = dtRuta.getNombre();
        this.descripcion = dtRuta.getDescripcion();
        this.duracion = dtRuta.getDuracion();
        this.costoTurista = dtRuta.getCostoTurista();
        this.costoEjecutivo = dtRuta.getCostoEjecutivo();
        this.equipajeExtra = dtRuta.getEquipajeExtra();
        this.fechaAlta = dtRuta.getFechaAlta();
        this.urlImagen = dtRuta.getUrlImagen();

        EstadoRuta estadoRuta;

        if(dtRuta.getEstado() == com.app.enums.EstadoRuta.APROBADA){
            estadoRuta = EstadoRuta.APROBADA;
        }else if(dtRuta.getEstado() == com.app.enums.EstadoRuta.RECHAZADA){
            estadoRuta = EstadoRuta.RECHAZADA;
        }else{
            estadoRuta = EstadoRuta.INGRESADA;
        }

        this.estado = estadoRuta;

        List<Categoria> listaCategorias = new ArrayList<>();
        List<com.app.clases.Categoria> categoriaList = dtRuta.getCategorias();

        for(com.app.clases.Categoria c : categoriaList){
            listaCategorias.add(new Categoria(c.getNombre()));
        }

        this.categorias = listaCategorias;
        this.ciudadOrigen = new Ciudad(dtRuta.getCiudadOrigen());
        this.ciudadDestino = new Ciudad(dtRuta.getCiudadDestino());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public float getCostoTurista() {
        return costoTurista;
    }

    public void setCostoTurista(float costoTurista) {
        this.costoTurista = costoTurista;
    }

    public float getCostoEjecutivo() {
        return costoEjecutivo;
    }

    public void setCostoEjecutivo(float costoEjecutivo) {
        this.costoEjecutivo = costoEjecutivo;
    }

    public float getEquipajeExtra() {
        return equipajeExtra;
    }

    public void setEquipajeExtra(float equipajeExtra) {
        this.equipajeExtra = equipajeExtra;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getUrlImagen() {return urlImagen;}

    public void setUrlImagen(String urlImagen) {this.urlImagen = urlImagen;}

    public EstadoRuta getEstado() {return estado;}

    public void setEstado(EstadoRuta estado) {this.estado = estado;}

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    @Override
    public String toString() { return this.nombre; }
}