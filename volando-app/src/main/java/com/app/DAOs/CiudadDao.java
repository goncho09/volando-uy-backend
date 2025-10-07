package com.app.DAOs;

import com.app.clases.Ciudad;
import com.app.clases.CiudadId;
import jakarta.persistence.EntityManager;

import java.util.Map;

public class CiudadDao extends BaseDao<Ciudad, CiudadId>{

    public CiudadDao(EntityManager em) {
        super(em, Ciudad.class);
    }

    public Map<String, Ciudad> obtenerCiudades() {
        return super.obtener(Ciudad::listarId);
    }

}
