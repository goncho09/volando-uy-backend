package uy.volando.servlets.RutasDeVuelo;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.Categoria;
import com.app.clases.Ciudad;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtCategoria;
import com.app.datatypes.DtCiudad;
import com.app.datatypes.DtRuta;
import com.app.enums.EstadoRuta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CrearRutaServlet", urlPatterns = {"/rutaDeVuelo/crear"})
public class CrearRutaServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            // Verificar que el usuario sea una aerolínea
            if (session == null || session.getAttribute("usuarioTipo") == null ||
                    !session.getAttribute("usuarioTipo").equals("aerolinea")) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            // Obtener datos para formulario
            List<DtCiudad> dtCiudades = sistema.listarCiudades();
            List<DtCategoria> dtCategorias = getCategoriasDisponibles();

            request.setAttribute("ciudades", dtCiudades);
            request.setAttribute("categorias", dtCategorias);

            request.getRequestDispatcher("/WEB-INF/jsp/rutaDeVuelo/crear.jsp").forward(request, response);

        } catch (Exception ex) {
            System.err.println("Error en CrearRutaServlet (GET): " + ex.getMessage());
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        try {
            HttpSession session = request.getSession(false);
            if (session == null || !"aerolinea".equals(session.getAttribute("usuarioTipo"))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("No autorizado");
                return;
            }

            // Obtener parámetros
            String nombre = request.getParameter("nombre");
            String descripcionCorta = request.getParameter("descripcionCorta");
            String descripcion = request.getParameter("descripcion");
            String horaStr = request.getParameter("hora");
            String costoTuristaStr = request.getParameter("costoTurista");
            String costoEjecutivoStr = request.getParameter("costoEjecutivo");
            String costoEquipajeExtraStr = request.getParameter("costoEquipajeExtra");
            String ciudadOrigenStr = request.getParameter("ciudadOrigen");
            String ciudadDestinoStr = request.getParameter("ciudadDestino");
            String[] categoriasArray = request.getParameterValues("categorias");

            // Validar campos obligatorios
            if (nombre == null || descripcionCorta == null || descripcion == null || horaStr == null ||
                    costoTuristaStr == null || costoEjecutivoStr == null || costoEquipajeExtraStr == null ||
                    ciudadOrigenStr == null || ciudadDestinoStr == null || categoriasArray == null) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Por favor, complete todos los campos obligatorios.");
                return;
            }

            // Validar que el nombre sea único
            if (sistema.existeRuta(nombre)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Ya existe una ruta de vuelo con ese nombre.");
                return;
            }

            // Convertir y validar tipos de datos
            float costoTurista, costoEjecutivo, costoEquipajeExtra;
            try {
                costoTurista = Float.parseFloat(costoTuristaStr);
                costoEjecutivo = Float.parseFloat(costoEjecutivoStr);
                costoEquipajeExtra = Float.parseFloat(costoEquipajeExtraStr);

                if (costoTurista <= 0 || costoEjecutivo <= 0 || costoEquipajeExtra < 0) {
                    throw new NumberFormatException("Los costos deben ser positivos");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Formato de costo inválido. Use números positivos.");
                return;
            }

            // Validar hora y convertir a duración (LocalTime)
            LocalTime duracion;
            try {
                duracion = LocalTime.parse(horaStr);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Formato de hora inválido. Use HH:MM");
                return;
            }

            // Validar ciudades
            if (ciudadOrigenStr.equals(ciudadDestinoStr)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("La ciudad origen y destino no pueden ser la misma.");
                return;
            }

            // Obtener objetos Ciudad desde los strings
            Ciudad ciudadOrigen = obtenerCiudadDesdeString(ciudadOrigenStr);
            Ciudad ciudadDestino = obtenerCiudadDesdeString(ciudadDestinoStr);

            if (ciudadOrigen == null || ciudadDestino == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Ciudad origen o destino no válida.");
                return;
            }

            // Obtener categorías
            List<String> nombresCategorias = new ArrayList<>();
            for (String catNombre : categoriasArray) {
                nombresCategorias.add(catNombre);
            }
            List<Categoria> categoriasSeleccionadas = sistema.getCategoriasPorNombre(nombresCategorias);

            if (categoriasSeleccionadas.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("No se encontraron las categorías seleccionadas.");
                return;
            }

            // Obtener aerolínea desde la sesión
            String nicknameAerolinea = (String) session.getAttribute("usuarioNickname");
            DtAerolinea aerolinea = sistema.getAerolinea(nicknameAerolinea);

            if (aerolinea == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Aerolínea no encontrada.");
                return;
            }

            // Crear DtRuta
            String imagen = "pictures/rutas/default.png";
            LocalDate fechaAlta = LocalDate.now();

            DtRuta ruta = new DtRuta(
                    nombre,
                    descripcion,
                    descripcionCorta,
                    duracion,
                    costoTurista,
                    costoEjecutivo,
                    costoEquipajeExtra,
                    fechaAlta,
                    imagen,
                    categoriasSeleccionadas,
                    ciudadOrigen,
                    ciudadDestino
            );

            // Usar el método del sistema para crear la ruta
            sistema.altaRutaDeVuelo(nicknameAerolinea, ruta);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Ruta de vuelo creada con éxito");

        } catch (Exception ex) {
        System.err.println("=== ERROR DETALLADO ===");
        System.err.println("Error en CrearRutaServlet (POST): " + ex.getMessage());

        System.err.println("=== FIN ERROR ===");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("Error: " + ex.getMessage());
    }
    }

    // Auxiliar para obtener Ciudad desde string
    private Ciudad obtenerCiudadDesdeString(String ciudadStr) {
        try {
            // Asumiendo que el formato es "Nombre, País" o solo "Nombre"
            String[] partes = ciudadStr.split(",");
            String nombreCiudad = partes[0].trim();
            String pais = partes.length > 1 ? partes[1].trim() : "Uruguay"; // País por defecto

            return sistema.buscarCiudad(nombreCiudad, pais);
        } catch (Exception e) {
            System.err.println("Error al obtener ciudad: " + ciudadStr + " - " + e.getMessage());
            return null;
        }
    }

    // Auxiliar para obtener categorías disponibles
    private List<DtCategoria> getCategoriasDisponibles() {
        List<DtCategoria> dtCategorias = new ArrayList<>();
        try {
            List<Categoria> categorias = sistema.getCategorias();
            for (Categoria categoria : categorias) {
                // Crear DtCategoria desde Categoria
                dtCategorias.add(new DtCategoria(
                        categoria.getNombre()
                ));
            }
        } catch (Exception e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
            // Categorías por defecto
            dtCategorias.add(new DtCategoria("Nacionales"));
            dtCategorias.add(new DtCategoria("Internacionales"));
            dtCategorias.add(new DtCategoria("Europa"));
            dtCategorias.add(new DtCategoria("América"));
            dtCategorias.add(new DtCategoria("Exclusivos"));
            dtCategorias.add(new DtCategoria("Temporada"));
            dtCategorias.add(new DtCategoria("Cortos"));
        }
        return dtCategorias;
    }
}