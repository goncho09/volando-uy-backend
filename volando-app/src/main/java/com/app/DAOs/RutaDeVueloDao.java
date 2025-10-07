package com.app.DAOs;

import com.app.clases.Categoria;
import com.app.clases.Ciudad;
import com.app.clases.Paquete;
import com.app.clases.RutaDeVuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RutaDeVueloDao extends BaseDao<RutaDeVuelo, String>{

    public RutaDeVueloDao(EntityManager em) {
        super(em, RutaDeVuelo.class);
    }

    public Map<String, RutaDeVuelo> obtenerRutasDeVuelo() {
        return super.obtener(RutaDeVuelo::getNombre);
    }

    public void addCategoria(RutaDeVuelo rt, Categoria categoria) {
        rt.getCategorias().add(categoria);
        actualizar(rt);
    }

    public void removeCategoria(RutaDeVuelo rt, Categoria categoria) {
        rt.getCategorias().remove(categoria);
        actualizar(rt);
    }
}
