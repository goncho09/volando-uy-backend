package com.app.clases;

import com.app.enums.TipoAsiento;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "rutaEnPaquete")
public class RutaEnPaquete {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int cantidad;
    private TipoAsiento tipoAsiento;

    @ManyToOne
    @JoinColumn(name="rutaDeVuelo_name", nullable = false)
    private RutaDeVuelo rutaDeVuelo;

    public RutaEnPaquete(){}
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
