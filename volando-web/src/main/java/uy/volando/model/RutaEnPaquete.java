package uy.volando.model;

import uy.volando.enums.TipoAsiento;

public class RutaEnPaquete {
    private int cantidad;
    private TipoAsiento tipoAsiento;
    private RutaDeVuelo rutaDeVuelo;

    public RutaEnPaquete(int cantidad, TipoAsiento tipoAsiento, RutaDeVuelo rutaDeVuelo) {
        this.cantidad = cantidad;
        this.tipoAsiento = tipoAsiento;
        this.rutaDeVuelo = rutaDeVuelo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public RutaDeVuelo getRutaDeVuelo() {
        return rutaDeVuelo;
    }

    public void setRutaDeVuelo(RutaDeVuelo rutaDeVuelo) {
        this.rutaDeVuelo = rutaDeVuelo;
    }


    @Override
    public String toString() {return this.rutaDeVuelo.getNombre();}
}
