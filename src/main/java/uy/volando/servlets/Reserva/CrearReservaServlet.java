package uy.volando.servlets.Reserva;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.RutaEnPaquete;
import com.app.datatypes.DtCliente;
import com.app.datatypes.*;

import com.app.enums.EstadoRuta;
import com.app.enums.MetodoPago;
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

            aerolineas.removeIf(aerolinea -> {
                List<DtRuta> rutas = aerolinea.listarRutasDeVuelo();
                if (rutas == null || rutas.isEmpty()) {
                    return true;
                }
                for (DtRuta ruta : rutas) {
                    if (ruta.getEstado() == EstadoRuta.APROBADA) {
                        List<DtVuelo> vuelos = sistema.listarVuelos(ruta.getNombre());
                        if (vuelos != null && !vuelos.isEmpty()) {
                            return false;
                        }
                    }
                }

                return true;
            });

            request.setAttribute("aerolineas", aerolineas);

            if (idAerolinea != null) {
                DtAerolinea aerolinea = sistema.getAerolinea(idAerolinea);
                List<DtRuta> rutasAerolinea = aerolinea.listarRutasDeVuelo();

                rutasAerolinea.removeIf(ruta -> (ruta.getEstado() != EstadoRuta.APROBADA));
                rutasAerolinea.removeIf(ruta -> (sistema.listarVuelos(ruta.getNombre()).isEmpty()));

                request.setAttribute("rutas", rutasAerolinea);
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
                    for (DtRutaEnPaquete rep : paquete.getRutaEnPaquete()) {
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
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
                request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
                return;
            }

            if (!"cliente".equals(session.getAttribute("usuarioTipo"))) {
                request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
                return;
            }

            try {
                String nicknameCliente = (String) session.getAttribute("usuarioNickname");
                DtCliente clienteLogueado = sistema.getCliente(nicknameCliente);

                String aerolinea = request.getParameter("aerolinea");
                String vuelo = request.getParameter("vuelo");
                String cantidad = request.getParameter("cantidad-pasajes");
                String tipoAsientoStr = request.getParameter("tipo-asiento");
                String equipajeExtra = request.getParameter("equipaje-extra");
                String metodoPagoStr = request.getParameter("metodo-pago");
                String paqueteNombre = request.getParameter("paquete");

                String[] nombres = request.getParameterValues("nombrePasajero");
                String[] apellidos = request.getParameterValues("apellidoPasajero");

                if (aerolinea == null || vuelo == null || tipoAsientoStr == null || cantidad == null || equipajeExtra == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Faltan datos obligatorios");
                    return;
                }

                int cantPasajes = Integer.parseInt(cantidad);
                int equipaje = Integer.parseInt(equipajeExtra);
                TipoAsiento tipo = TipoAsiento.valueOf(tipoAsientoStr.toUpperCase());

                List<DtPasajero> pasajeros = new ArrayList<>();
                if (nombres != null && apellidos != null) {
                    for (int i = 0; i < Math.min(nombres.length, apellidos.length); i++) {
                        pasajeros.add(new DtPasajero(nombres[i], apellidos[i]));
                    }
                }

                if (pasajeros.size() != cantPasajes) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("La cantidad de pasajeros no coincide con la cantidad indicada");
                    return;
                }


                DtVuelo vueloSeleccionado = sistema.getVuelo(vuelo);
                if (vueloSeleccionado == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Vuelo no encontrado");
                    return;
                }

                MetodoPago metodoPago;
                DtPaquete paqueteSeleccionado = null;

                if ("pago-paquete".equals(metodoPagoStr)) {
                    metodoPago = MetodoPago.PAQUETE;
                    if (paqueteNombre == null || paqueteNombre.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Debe seleccionar un paquete");
                        return;
                    }

                    List<DtPaquete> paquetesCliente = sistema.listarPaquetes(clienteLogueado);
                    for (DtPaquete p : paquetesCliente) {
                        if (p.getNombre().equals(paqueteNombre)) {
                            paqueteSeleccionado = p;
                            break;
                        }
                    }

                    if (paqueteSeleccionado == null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Paquete inválido");
                        return;
                    }

                } else if ("pago-general".equals(metodoPagoStr)) {
                    metodoPago = MetodoPago.GENERAL;
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Debe seleccionar un método de pago");
                    return;
                }

                DtReserva reserva;
                LocalDate fecha = LocalDate.now();

                if (metodoPago == MetodoPago.PAQUETE) {
                    reserva = new DtReserva(fecha, tipo, cantPasajes, equipaje, 0, pasajeros, clienteLogueado, vueloSeleccionado, metodoPago, paqueteSeleccionado);
                } else {
                    reserva = new DtReserva(fecha, tipo, cantPasajes, equipaje, 0, pasajeros, clienteLogueado, vueloSeleccionado, metodoPago);
                }

                sistema.altaReserva(reserva);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Reserva creada con éxito");
                request.setAttribute("exito", "Reserva creada con éxito");
                request.getRequestDispatcher("/WEB-INF/jsp/reservas/crearReserva.jsp").forward(request, response);

            } catch (IllegalArgumentException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Error: " + ex.getMessage());
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error del servidor: " + ex.getMessage());
                ex.printStackTrace();
            }
        }




}
