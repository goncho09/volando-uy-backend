package com.app.datatypes;

import com.app.clases.Cliente;
import com.app.clases.Paquete;
import com.app.clases.Vuelo;
import com.app.enums.TipoAsiento;
import com.app.enums.MetodoPago;

import java.time.LocalDate;
import java.util.List;

public class DtReserva {
    private LocalDate fecha;
    private TipoAsiento tipoAsiento;
    private int cantPasajes;
    private int equipajeExtra;
    private float costo;
    private List<DtPasajero> pasajeros;
    private Cliente cliente;
    private Vuelo vuelo;
    private Paquete paquete;
    private MetodoPago metodoPago;
    private DtPaquete paquetePago;

    public DtReserva() {}

    public DtReserva(LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes, int equipajeExtra, float costo, List<DtPasajero> pasajeros, Cliente cliente, Vuelo vuelo, Paquete paquete, MetodoPago metodoPago) {
        this.fecha = fecha;
        this.tipoAsiento = tipoAsiento;
        this.cantPasajes = cantPasajes;
        this.equipajeExtra = equipajeExtra;
        this.costo = costo;
        this.pasajeros = pasajeros;
        this.cliente = cliente;
        this.vuelo = vuelo;
        this.paquete = paquete;
        this.metodoPago = metodoPago;
    }

    public DtReserva(LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes, int equipajeExtra, float costo, List<DtPasajero> pasajeros, Cliente cliente, Vuelo vuelo) {
        this.fecha = fecha;
        this.tipoAsiento = tipoAsiento;
        this.cantPasajes = cantPasajes;
        this.equipajeExtra = equipajeExtra;
        this.costo = costo;
        this.pasajeros = pasajeros;
        this.cliente = cliente;
        this.vuelo = vuelo;
        this.paquete = null;
    }

    public DtReserva(LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes, int equipajeExtra, float costo, List<DtPasajero> pasajeros, Cliente cliente, Paquete paquete) {
        this.fecha = fecha;
        this.tipoAsiento = tipoAsiento;
        this.cantPasajes = cantPasajes;
        this.equipajeExtra = equipajeExtra;
        this.costo = costo;
        this.pasajeros = pasajeros;
        this.cliente = cliente;
        this.vuelo = null;
        this.paquete = paquete;
    }

    public DtReserva(LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes, int equipajeExtra,List<DtPasajero> pasajeros, MetodoPago metodoPago, DtPaquete paquetePago) {
        this.fecha = fecha;
        this.tipoAsiento = tipoAsiento;
        this.cantPasajes = cantPasajes;
        this.equipajeExtra = equipajeExtra;
        this.pasajeros = pasajeros;
        this.metodoPago = metodoPago;
        this.paquetePago = paquetePago;
    }

    public DtReserva(LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes, int equipajeExtra,List<DtPasajero> pasajeros, MetodoPago metodoPago) {
        this.fecha = fecha;
        this.tipoAsiento = tipoAsiento;
        this.cantPasajes = cantPasajes;
        this.equipajeExtra = equipajeExtra;
        this.pasajeros = pasajeros;
        this.metodoPago = metodoPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public int getCantPasajes() {
        return cantPasajes;
    }

    public void setCantPasajes(int cantPasajes) {
        this.cantPasajes = cantPasajes;
    }

    public int getEquipajeExtra() {
        return equipajeExtra;
    }

    public void setEquipajeExtra(int equipajeExtra) {
        this.equipajeExtra = equipajeExtra;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public List<DtPasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<DtPasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public DtPaquete getPaquetePago() {
        return paquetePago;
    }

    public void setPaquetePago(DtPaquete paquetePago) {
        this.paquetePago = paquetePago;
    }


    @Override
    public String toString() {
        return this.getCliente().getNickname() + " - " + this.getVuelo().getNombre();
    }

}
