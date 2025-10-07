package com.app.DAOs;

import com.app.datatypes.DtPasajero;
import jakarta.persistence.EntityManager;

public class PasajerosDao extends BaseDao<DtPasajero, Long> {
    public PasajerosDao(EntityManager em) {
        super(em, DtPasajero.class);
    }
}
