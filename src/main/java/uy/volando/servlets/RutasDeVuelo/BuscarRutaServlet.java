package uy.volando.servlets.RutasDeVuelo;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtRuta;
import com.app.datatypes.DtVuelo;
import com.app.enums.EstadoRuta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RutaServlet", urlPatterns = {"/ruta-de-vuelo/buscar"})
public class BuscarRutaServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BuscarRutaServlet.class.getName());
    ISistema s = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        System.out.println(">>> BuscarRutaServlet: Session = " + (session != null ? "OK" : "NULL"));  // Debug temporal

        try {
            String nombreRuta = request.getParameter("nombre");
            if (nombreRuta == null || nombreRuta.trim().isEmpty()) {
                throw new IllegalArgumentException("Nombre de ruta requerido");
            }

            DtRuta ruta = s.consultarRuta(nombreRuta);
            if (ruta == null) {
                throw new Exception("Ruta no encontrada");
            }
            if (ruta.getEstado() != EstadoRuta.APROBADA) {
                throw new Exception("Ruta no disponible");
            }

            // Procesar imagen de ruta
            String basePath = request.getServletContext().getRealPath("/pictures/rutas");
            String contextPath = request.getContextPath();
            String urlImagen = ruta.getUrlImagen();
            File rutaImg = null;

            if (urlImagen != null && !urlImagen.isEmpty()) {
                rutaImg = new File(basePath, urlImagen);
            }

            if (urlImagen == null || urlImagen.isEmpty() || !rutaImg.exists()) {
                ruta.setUrlImagen(contextPath + "/assets/rutaDefault.png");
            } else {
                ruta.setUrlImagen(contextPath + "/pictures/rutas/" + urlImagen);
            }

            // Procesar vuelos
            List<DtVuelo> vueloList = s.getVuelosRutaDeVuelo(ruta);
            vueloList.removeIf(vuelo -> vuelo.getFecha().isBefore(LocalDate.now()));

            for (DtVuelo vuelo : vueloList) {
                basePath = request.getServletContext().getRealPath("/pictures/vuelos");  // Reasigna basePath

                String urlImage = vuelo.getUrlImage();
                File vueloImg = null;

                if (urlImage != null && !urlImage.isEmpty()) {
                    vueloImg = new File(basePath, urlImage);
                }

                if (urlImage == null || urlImage.isEmpty() || !vueloImg.exists()) {
                    vuelo.setUrlImage(contextPath + "/assets/vueloDefault.jpg");
                } else {
                    vuelo.setUrlImage(contextPath + "/pictures/vuelos/" + urlImage);
                }
            }

            // Procesar paquetes
            List<DtPaquete> paqueteList = s.listarPaquetesNoComprados();
            paqueteList.removeIf(paquete -> paquete.getRutaDeVuelo(ruta.getNombre()) == null);

            // Check de owner (solo si hay sesión)
            boolean allowed = false;
            if (session != null &&  // <-- FIX: Check null aquí
                    session.getAttribute("usuarioTipo") != null &&
                    session.getAttribute("usuarioNickname") != null &&
                    "aerolinea".equals(session.getAttribute("usuarioTipo"))) {

                String nickname = session.getAttribute("usuarioNickname").toString();
                System.out.println(">>> BuscarRuta: Chequeando owner para " + nickname);  // Debug

                DtAerolinea a = s.getAerolinea(nickname);
                if (a != null) {
                    List<DtRuta> rutasAerolinea = a.listarRutasDeVuelo();
                    if (!rutasAerolinea.isEmpty()) {
                        for (DtRuta r : rutasAerolinea) {
                            if (r.getNombre().equals(nombreRuta)) {
                                allowed = true;
                                break;
                            }
                        }
                    }
                }
            }

            // Setear atributos
            request.setAttribute("ruta", ruta);
            request.setAttribute("vuelos", vueloList);
            request.setAttribute("paquetes", paqueteList);
            request.setAttribute("rutaOwner", allowed);

            request.getRequestDispatcher("/WEB-INF/jsp/rutaDeVuelo/buscar.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println(">>> BuscarRutaServlet: Excepción = " + e.getMessage());  // Debug
            LOGGER.log(Level.SEVERE, "Error en BuscarRutaServlet: ", e);
            request.setAttribute("error", e.getMessage());  // Para mostrar en error.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}