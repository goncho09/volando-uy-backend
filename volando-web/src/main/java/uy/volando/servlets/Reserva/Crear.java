package uy.volando.servlets.Reserva;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.Reserva;
import com.app.clases.Sistema;
import com.app.clases.Cliente;

import com.app.datatypes.DtVuelo;
import com.app.datatypes.DtCliente;
import com.app.datatypes.DtReserva;
import com.app.datatypes.DtPasajero;
import com.app.enums.TipoAsiento;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Crear", urlPatterns = {"/Reserva/Crear"})

public class Crear extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/reservas/crearReserva.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws jakarta.servlet.ServletException, java.io.IOException {

        try {
            ISistema sistema = Factory.getSistema();

            //Datos del form
            String aerolinea = request.getParameter("aerolinea-select");
            String vuelo = request.getParameter("vuelo-select");
            String cantidad = request.getParameter("cantidad-pasajes");
            String tipoAsiento = request.getParameter("tipo-asiento");
            String equipajeExtra = request.getParameter("equipaje-extra");

            //obtener la sesion del usuario
            HttpSession session = request.getSession(false);
            if (session == null || !"cliente".equals(session.getAttribute("usuarioTipo"))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Debés iniciar sesión como cliente");
                return;
            }

            String nicknameCliente = (String) session.getAttribute("usuarioNickname");

            //buscar si existe el cliente
            DtCliente clienteLogueado = null;
            for (DtCliente c : sistema.listarClientes()) {
                if (c.getNickname().equals(nicknameCliente)) {
                    clienteLogueado = c;
                    break;
                }
            }

            if(clienteLogueado == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Cliente no encontrado");
                return;
            }

            if (aerolinea == null || vuelo == null || tipoAsiento == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Faltan datos obligatorios");
                return;
            }

            //aca buscar el vuelo
            DtVuelo vueloSeleccionado = sistema.getVuelo(vuelo);
            if (vueloSeleccionado == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Vuelo no encontrado");
                return;
            }

            // aca lo de los pasajeros
            String[] nombres = request.getParameterValues("nombrePasajero");
            String[] apellidos = request.getParameterValues("apellidoPasajero");
            List<DtPasajero> pasajeros = new ArrayList<>();

            if (nombres != null && apellidos != null) {
                for (int i = 0; i < Math.min(nombres.length, apellidos.length); i++) {
                    pasajeros.add(new DtPasajero(nombres[i], apellidos[i]));
                }
            }

            LocalDate fecha = LocalDate.now();
            TipoAsiento tipo = TipoAsiento.valueOf(tipoAsiento.toUpperCase());
            int cantPasajes = Integer.parseInt(cantidad);
            int equipaje = Integer.parseInt(request.getParameter("equipaje-extra"));

            DtReserva reserva = new DtReserva(fecha, tipo, cantPasajes, equipaje, pasajeros);

            //dar alta reserva
            sistema.altaReserva(reserva, clienteLogueado, vueloSeleccionado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Reserva creada con éxito");

        }catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }

    }

}
