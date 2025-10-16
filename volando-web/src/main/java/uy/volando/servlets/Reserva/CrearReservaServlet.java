package uy.volando.servlets.Reserva;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.RutaEnPaquete;
import com.app.datatypes.DtCliente;
import com.app.datatypes.*;

import com.app.enums.TipoAsiento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Crear", urlPatterns = {"/reservas/crear"})

public class CrearReservaServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        if (session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        if (!session.getAttribute("usuarioTipo").equals("cliente")) {
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        try {
            String nicknameCliente = (String) session.getAttribute("usuarioNickname");
            DtCliente cliente = sistema.getCliente(nicknameCliente);

            String idAerolinea = request.getParameter("aerolinea");
            String idRuta = request.getParameter("ruta");

            List<DtAerolinea> aerolineas = sistema.listarAerolineas();
            aerolineas.removeIf(a -> a.listarRutasDeVuelo().isEmpty());
            request.setAttribute("aerolineas", aerolineas);

            if (idAerolinea != null) {
                DtAerolinea aerolinea = sistema.getAerolinea(idAerolinea);
                request.setAttribute("rutas", aerolinea.listarRutasDeVuelo());
                request.setAttribute("aerolineaId", idAerolinea);
            }

            if (idRuta != null) {
                DtRuta ruta = sistema.getRutaDeVuelo(idRuta);
                if (ruta != null) {
                    double precioTurista = ruta.getCostoTurista();
                    double precioEjecutivo = ruta.getCostoEjecutivo();
                    double precioEquipaje = ruta.getEquipajeExtra();

                    request.setAttribute("precioTurista", precioTurista);
                    request.setAttribute("precioEjecutivo", precioEjecutivo);
                    request.setAttribute("precioEquipaje", precioEquipaje);
                }
            }

            if (idRuta != null && idAerolinea != null) {
                DtAerolinea aerolinea = sistema.getAerolinea(idAerolinea);
                request.setAttribute("aerolineaId", idAerolinea);
                request.setAttribute("rutaId", idRuta);
                request.setAttribute("rutas", aerolinea.listarRutasDeVuelo());
                request.setAttribute("vuelos", sistema.listarVuelos(idRuta));
            }

            List<DtPaquete> paquetesCliente = sistema.listarPaquetes(cliente);
            List<DtPaquete> paquetesFiltrados = new ArrayList<>();
            LocalDate hoy = LocalDate.now();

            if (idRuta != null) {
                for (DtPaquete paquete : paquetesCliente) {
                    for (RutaEnPaquete rep : paquete.getRutaEnPaquete()) {
                        if (rep.getRutaDeVuelo() != null && rep.getRutaDeVuelo().getNombre().equals(idRuta)) {
                            paquetesFiltrados.add(paquete);
                            break;
                        }
                    }
                }
            } else {
                paquetesFiltrados.addAll(paquetesCliente);
            }
            request.setAttribute("paquetes", paquetesFiltrados);

            request.getRequestDispatcher("/WEB-INF/jsp/reservas/crearReserva.jsp").forward(request, response);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
            System.out.println("Error en CrearReservaServlet: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
        }
    }

        @Override
        protected void doPost (HttpServletRequest request, HttpServletResponse response)throws
        jakarta.servlet.ServletException, java.io.IOException {

            HttpSession session = request.getSession(false);

            if (session == null) {
                request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
                return;
            }

            if (session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
                request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
                return;
            }

            if (!session.getAttribute("usuarioTipo").equals("cliente")) {
                request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
                return;
            }

            try {
                ISistema sistema = Factory.getSistema();

                //Datos del form
                String aerolinea = request.getParameter("aerolinea");
                String vuelo = request.getParameter("vuelo");
                String cantidad = request.getParameter("cantidad-pasajes");
                String tipoAsiento = request.getParameter("tipo-asiento");
                String equipajeExtra = request.getParameter("equipaje-extra");

                String nicknameCliente = (String) session.getAttribute("usuarioNickname");
                DtCliente clienteLogueado = sistema.getCliente(nicknameCliente);

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
                int equipaje = Integer.parseInt(equipajeExtra);

                DtReserva reserva = new DtReserva(fecha, tipo, cantPasajes, equipaje, pasajeros);

                //dar alta reserva
                sistema.altaReserva(reserva, clienteLogueado, vueloSeleccionado);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Reserva creada con éxito");

            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error del servidor: " + ex.getMessage());
            }

        }

    }
