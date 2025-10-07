package com.app.DAOs;

import com.app.clases.Categoria;
import jakarta.persistence.EntityManager;

import java.util.Map;

public class CategoriaDao extends BaseDao<Categoria, String> {

    public CategoriaDao(EntityManager em) {
        super(em, Categoria.class);
    }

    public Map<String, Categoria> obtenerCategorias() {
        return super.obtener(Categoria::getNombre);
    }

}
