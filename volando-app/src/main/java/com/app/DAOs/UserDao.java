package com.app.DAOs;

import com.app.clases.*;
import com.app.clases.Usuario;
import jakarta.persistence.EntityManager;

import java.util.Map;

public class UserDao extends BaseDao<Usuario, String>{

    public UserDao(EntityManager em) {
        super(em, Usuario.class);
    }

    public Map<String, Usuario> obtenerUsuarios() {
        return super.obtener(Usuario::getNickname);
    }

    // ----------- AEROLINEAS ----------- //

    public void addRutaDeVuelo(Aerolinea aerolinea, RutaDeVuelo ruta){
        aerolinea.getRutasDeVuelo().add(ruta);
        actualizar(aerolinea);
    }

    public void removeRutaDeVuelo(Aerolinea aerolinea, RutaDeVuelo ruta){
        aerolinea.getRutasDeVuelo().remove(ruta);
        actualizar(aerolinea);
    }

    // ------------ CLIENTES ----------- //

    public void addCompraPaquete(Cliente cliente, CompraPaquete cp){
        cliente.getComprasPaquetes().add(cp);
        actualizar(cliente);
    }

    public void removeCompraPaquete(Cliente cliente, CompraPaquete cp){
        cliente.getComprasPaquetes().remove(cp);
        actualizar(cliente);
    }

    public void addReserva(Cliente cliente, Reserva reserva){
        cliente.getReservas().add(reserva);
        actualizar(cliente);
    }

    public void removeReserva(Cliente cliente, Reserva reserva){
        cliente.getReservas().remove(reserva);
        actualizar(cliente);
    }

}
