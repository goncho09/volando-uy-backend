package uy.volando.servlets.Vuelo;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.RutaDeVuelo;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtRuta;
import com.app.datatypes.DtVuelo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet (name = "CrearVueloServlet", urlPatterns = {"/vuelo/crear","/flight/new", "/flight/create", "/vuelo/new", "/vuelo/create"})
public class CrearVueloServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        try{
            HttpSession session = request.getSession(false);

            if (session.getAttribute("usuarioTipo") == null || !session.getAttribute("usuarioTipo").equals("aerolinea")) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            DtAerolinea aerolinea = sistema.getAerolinea(session.getAttribute("usuarioNickname").toString());

            if (aerolinea == null || !aerolinea.getNickname().equals(session.getAttribute("usuarioNickname").toString())) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            List<DtRuta> rutas = sistema.listarRutasDeVuelo(aerolinea);
            request.setAttribute("rutas", rutas);

            request.getRequestDispatcher("/WEB-INF/jsp/vuelo/crear.jsp").forward(request, response);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        response.setContentType("text/plain;charset=UTF-8");
        try {

            String nombre = request.getParameter("nombre");
            String duracionStr = request.getParameter("duracion");
            String fechaStr = request.getParameter("fecha");
            String maxEjecutivosStr = request.getParameter("max-ejecutivos");
            String maxTuristasStr = request.getParameter("max-turistas");
            String imagen = "pictures/vuelos/default.png";
            //String imagenStr = request.getParameter("image");
            String rutaStr = request.getParameter("ruta");



            if (nombre == null || duracionStr == null || fechaStr == null || maxEjecutivosStr == null || maxTuristasStr == null || rutaStr == null ||
                nombre.isEmpty() || duracionStr.isEmpty() || fechaStr.isEmpty() || maxEjecutivosStr.isEmpty() || maxTuristasStr.isEmpty() || rutaStr.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Por favor, complete todos los campos obligatorios.");
                return;
            }

            if (sistema.existeVuelo(nombre)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Ya existe un vuelo con ese nombre");
                return;
            }

            int maxEjecutivos = Integer.parseInt(maxEjecutivosStr);
            int maxTuristas = Integer.parseInt(maxTuristasStr);

            if (maxEjecutivos == 0 && maxTuristas == 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("El vuelo debe tener al menos una plaza disponible");
                return;
            }

            LocalTime duracion;
            try{
                duracion = LocalTime.parse(duracionStr);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Formato de duración inválido. Use HH:MM");
                return;
            }

            LocalDate fecha;
            try{
                fecha = LocalDate.parse(fechaStr);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Formato de fecha inválido. Use AAAA-MM-DD");
                return;
            }

            if (fecha.isBefore(LocalDate.now())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("La fecha del vuelo no puede ser en el pasado.");
                return;
            }


            sistema.seleccionarAerolineaParaVuelo(request.getSession().getAttribute("usuarioNickname").toString());
            sistema.seleccionarRuta(rutaStr);
            sistema.ingresarDatosVuelo(new DtVuelo(nombre, fecha, duracion, maxTuristas, maxEjecutivos, imagen, LocalDate.now(),null, 0));
            sistema.confirmarAltaVuelo();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Paquete creado con éxito");

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }
    }
}
