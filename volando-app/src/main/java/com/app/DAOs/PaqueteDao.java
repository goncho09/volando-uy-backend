package com.app.DAOs;

import com.app.clases.Aerolinea;
import com.app.clases.Paquete;
import com.app.clases.RutaDeVuelo;
import com.app.clases.RutaEnPaquete;
import jakarta.persistence.EntityManager;

import java.util.Map;

public class PaqueteDao extends BaseDao<Paquete, String> {

    public PaqueteDao(EntityManager em) {
        super(em, Paquete.class);
    }

    public Map<String, Paquete> obtenerPaquetes() {
        return super.obtener(Paquete::getNombre);
    }

    public void addRutaEnPaquete(Paquete p, RutaEnPaquete rp){
        p.getRutaEnPaquete().add(rp);
        actualizar(p);
    }

    public void removeRutaEnPaquete(Paquete p, RutaEnPaquete rp){
        p.getRutaEnPaquete().remove(rp);
        actualizar(p);
    }

    public int actualizarCantidadRutaEnPaquete(Paquete p, RutaEnPaquete rp,int cantidad){
        rp.setCantidad(rp.getCantidad() + cantidad);
        actualizar(p);
        return  rp.getCantidad();
    }

}
