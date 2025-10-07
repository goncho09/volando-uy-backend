package com.app.DAOs;

import com.app.clases.CompraPaquete;
import jakarta.persistence.EntityManager;

public class CompraPaqueteDao extends BaseDao<CompraPaquete, String>{

    public CompraPaqueteDao(EntityManager em) {
        super(em, CompraPaquete.class);
    }

}
