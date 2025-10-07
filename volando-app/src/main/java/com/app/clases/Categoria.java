package com.app.clases;

import com.app.datatypes.DtCategoria;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
//@Table(name="categoria") <- Agarra por defecto el nombre "Categoria"

public class Categoria {
    @Id
    private String nombre;

    public Categoria() {}
    public Categoria(DtCategoria categoria) {
        this.nombre = categoria.getNombre();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  @Override
    public String toString() {
        return this.nombre;
    }
}
