package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtRuta;
import com.app.enums.EstadoRuta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    ISistema s = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String busqueda = request.getParameter("busqueda");

            if (busqueda != null && !busqueda.isEmpty()) {

                if (s.existeRuta(busqueda)) {
                    DtRuta ruta = s.getRutaDeVuelo(busqueda);
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
                    request.setAttribute("ruta", ruta);

                }else if (s.existePaquete(busqueda)) {
                    DtPaquete paquete = s.getPaquete(busqueda);
                    request.setAttribute("paquete", paquete);
                }
            } else {
                request.getSession().setAttribute("usuario", null);

                List<DtRuta> listaRuta = s.listarRutasDeVuelo();

                listaRuta.removeIf(ruta -> ruta.getEstado() != EstadoRuta.APROBADA);

                for (DtRuta ruta : listaRuta) {
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
                }

                request.setAttribute("rutas", listaRuta);
            }

            // Forward to JSP page
            request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
