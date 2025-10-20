package com.app.clases;

import com.app.datatypes.*;
import com.app.enums.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Entity
@DiscriminatorValue("Cliente") // Cuando se haga la tabla de usuarios, éste en particular será "userType" Cliente
public class Cliente extends Usuario{
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private TipoDocumento tipoDocumento;
    private int numeroDocumento;

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true)// 1 - N (un cliente tiene muchas "compras"
    private List<CompraPaquete> comprasPaquetes = new ArrayList<>();

    @OneToMany(mappedBy = "cliente") // 1 - N (un cliente tiene muchas "reservas"
    private List<Reserva> reservas = new ArrayList<>();

    public Cliente(){}
    public Cliente(DtCliente cliente) {
        super(new DtUsuario(cliente.getNickname(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword(), cliente.getUrlImage()));
        this.apellido = cliente.getApellido();
        this.fechaNacimiento = cliente.getFechaNacimiento();
        this.nacionalidad = cliente.getNacionalidad();
        this.tipoDocumento = cliente.getTipoDocumento();
        this.numeroDocumento = cliente.getNumeroDocumento();
        this.comprasPaquetes = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public List<CompraPaquete> getComprasPaquetes() {
        return comprasPaquetes;
    }

    public void setComprasPaquetes(List<CompraPaquete> comprasPaquetes) {
        this.comprasPaquetes = comprasPaquetes;
    }

    public List<Reserva> getReservas(){
        return this.reservas;
    }

    public List<DtReserva> getDtReservas() {
        List<DtReserva> dtReservas = new ArrayList<>();
        for (Reserva reserva : this.reservas) {
            dtReservas.add(new DtReserva(
                    reserva.getFecha(),
                    reserva.getTipoAsiento(),
                    reserva.getCantPasajes(),
                    reserva.getEquipajeExtra(),
                    reserva.getCosto(),
                    reserva.getPasajeros(),
                    this,  // El cliente actual
                    reserva.getVuelo(),
                    reserva.getPaquete(),
                    reserva.getMetodoPago()
            ));
        }
        return dtReservas;
    }

    public List<DtPaquete> getPaquetesAdquiridos() {
        List<DtPaquete> dtPaquetes = new ArrayList<>();
        if (comprasPaquetes != null) {
            for (CompraPaquete compra : comprasPaquetes) {
                Paquete paquete = compra.getPaquete();
                dtPaquetes.add(new DtPaquete(
                        paquete.getNombre(),
                        paquete.getDescripcion(),
                        paquete.getValidezDias(),
                        paquete.getDescuento(),
                        paquete.getCosto(),
                        paquete.getRutaEnPaquete()
                ));
            }
        }
        return dtPaquetes;
    }

    public DtCliente getDatos(){
        return new DtCliente(
                this.getNickname(),
                this.getNombre(),
                this.getEmail(),
                this.getPassword(),
                this.getUrlImage(),
                this.getApellido(),
                this.getFechaNacimiento(),
                this.getNacionalidad(),
                this.getTipoDocumento(),
                this.getNumeroDocumento()
        );
    }

    public boolean existeVueloReserva(Vuelo vuelo){
        for(Reserva r : this.getReservas()){
            if(r.getVuelo().getNombre().equals(vuelo.getNombre())){
                return true;
            }
        }
        return false;
    };

    public void mostrarDatos() {
        System.out.println("Datos Usuario: " +
                nickname + " - " + nombre + " - " + apellido + " - " +
                email + " - " + fechaNacimiento + " - " + nacionalidad);
    }
}

