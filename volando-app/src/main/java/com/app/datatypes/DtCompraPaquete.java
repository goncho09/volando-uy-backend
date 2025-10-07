package com.app.datatypes;

import com.app.clases.Cliente;
import com.app.clases.Paquete;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class DtCompraPaquete {
    private long id;
    private LocalDate fechaCompra;
    private LocalDate fechaVencimiento;
    private float costo;
    private Paquete paquete;
    private Cliente cliente;

    public DtCompraPaquete() {};
    public DtCompraPaquete(LocalDate fechaCompra, LocalDate fechaVencimiento, float costo, Paquete paquete, Cliente cliente) {
        this.fechaCompra = fechaCompra;
        this.fechaVencimiento = fechaVencimiento;
        this.costo = costo;
        this.paquete = paquete;
        this.cliente = cliente;
    }


    public LocalDate getFechaCompra(){
        return fechaCompra;
    }

    public LocalDate getFechaVencimiento(){
        return fechaVencimiento;
    }

    public float getCosto(){
        return costo;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public Cliente getCliente() {
        return cliente;
    }

}
