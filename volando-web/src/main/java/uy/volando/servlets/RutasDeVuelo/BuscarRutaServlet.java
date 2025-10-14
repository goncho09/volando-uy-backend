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

        String nombreRuta = request.getParameter("nombre");

        try {
            DtRuta ruta = s.consultarRuta(nombreRuta);
            if(ruta.getEstado() != EstadoRuta.APROBADA){
                throw new Exception("Ruta no disponible");
            }

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

            List<DtVuelo> vueloList = s.getVuelosRutaDeVuelo(ruta);
            vueloList.removeIf(vuelo -> vuelo.getFecha().isBefore(LocalDate.now()));

            for(DtVuelo vuelo : vueloList){
                basePath = request.getServletContext().getRealPath("/pictures/vuelos");

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

            List<DtPaquete> paqueteList = s.listarPaquetes();
            paqueteList.removeIf(paquete -> paquete.getRutaDeVuelo(ruta.getNombre()) == null);

            boolean allowed = false;

            HttpSession session = request.getSession(false);
            if(session != null && session.getAttribute("usuarioNickname") != null && session.getAttribute("usuarioTipo") != null && session.getAttribute("usuarioTipo").equals("aerolinea")){
                DtAerolinea a = s.getAerolinea(session.getAttribute("usuarioNickname").toString());
                List<DtRuta> rutasAerolinea = a.listarRutasDeVuelo();
                if(!rutasAerolinea.isEmpty()){
                    for(DtRuta r : rutasAerolinea){
                        if(r.getNombre().equals(nombreRuta)){
                            allowed = true;
                            break;
                        }
                    }
                }
            }

            request.setAttribute("ruta", ruta);
            request.setAttribute("vuelos", vueloList);
            request.setAttribute("paquetes", paqueteList);
            request.setAttribute("rutaOwner", allowed);


        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error: ", e);

            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

        // Forward to JSP page
        request.getRequestDispatcher("/WEB-INF/jsp/rutaDeVuelo/buscar.jsp").forward(request, response);
    }


}
