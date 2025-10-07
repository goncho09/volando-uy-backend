package com.app.clases;

import com.app.datatypes.DtPasajero;
import com.app.datatypes.DtReserva;
import com.app.enums.TipoAsiento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate fecha;
    private TipoAsiento tipoAsiento;
    private int cantPasajes;
    private int equipajeExtra;
    private float costo;

    @OneToMany // Una reserva "puede" tener 1 o más pasajeros (1-N)
    @JoinColumn(name="reserva_id", nullable = false) // nullable = false indica que debe existir obligatoriamente 1 pasaje.
    private List<DtPasajero> pasajeros = new ArrayList<>();

    @ManyToOne(optional = false) // Muchas reserva es contenida por un cliente, el optional indica que debe existir un cliente sí o sí
    @JoinColumn(name="cliente_nickName")
    private Cliente cliente;

    @ManyToOne(optional = true) // Muchas reservas pueden tener 1 único vuelo asociado (N-1)
    @JoinColumn(name = "paquete_id")
    private Paquete paquete;

    // Relación opcional con Vuelo
    @ManyToOne(optional = true) // (N-1)
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @PrePersist
    @PreUpdate
    private void validarXor() { // Valida que la reserva tenga un vuelo ó una reserva, no ambas.
        if ((paquete == null && vuelo == null) || (paquete != null && vuelo != null)) {
            throw new IllegalStateException("Reserva debe tener exactamente un Paquete o un Vuelo");
        }
    }

    public Reserva() {}
    public Reserva(DtReserva reserva) {
        this.fecha = reserva.getFecha();
        this.tipoAsiento = reserva.getTipoAsiento();
        this.cantPasajes = reserva.getCantPasajes();
        this.equipajeExtra = reserva.getEquipajeExtra();
        this.costo = reserva.getCosto();
        this.pasajeros = reserva.getPasajeros();
        this.cliente = reserva.getCliente();
        this.vuelo = reserva.getVuelo();
        this.paquete = reserva.getPaquete();
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

    public DtReserva getDatos() {
        return new DtReserva(
                this.fecha,
                this.tipoAsiento,
                this.cantPasajes,
                this.equipajeExtra,
                this.costo,
                this.pasajeros,
                this.cliente,
                this.vuelo,
                this.paquete
        );
    }
}
