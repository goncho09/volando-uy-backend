package com.app.datatypes;

import com.app.clases.CompraPaquete;
import com.app.enums.TipoDocumento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DtCliente extends DtUsuario{
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private TipoDocumento tipoDocumento;
    private int numeroDocumento;
    private List<CompraPaquete> comprasPaquetes;

    public DtCliente() {}
    public DtCliente(String nickname, String nombre, String email, String password, String urlImage, String apellido, LocalDate fechaNacimiento, String nacionalidad, TipoDocumento tipoDocumento, int numeroDocumento) {
        super(nickname, nombre, email, password, urlImage);
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.comprasPaquetes = new ArrayList<>();
    }

    public DtCliente(DtUsuario usuario, String apellido, LocalDate fechaNacimiento, String nacionalidad, TipoDocumento tipoDocumento, int numeroDocumento) {
        super(usuario.getNickname(), usuario.getEmail(),usuario.getNombre(),  usuario.getPassword(), usuario.getUrlImage());
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.comprasPaquetes = new ArrayList<>();
        System.out.println("DtCliente creado con nickname: " + usuario.getNickname());
    }

    public DtCliente(String nickname, String nombre, String email, String apellido, LocalDate fechaNacimiento, String nacionalidad, TipoDocumento tipoDocumento, int numeroDocumento) {
        super(nickname, nombre, email);
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.comprasPaquetes = new ArrayList<>();
    }


    public String getApellido() {return apellido;}

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
}
