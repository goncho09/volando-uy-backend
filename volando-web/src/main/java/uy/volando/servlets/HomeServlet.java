package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
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

        // Set attributes to be used in JSP
        request.setAttribute("message", "Welcome to Volando UY!");
        request.setAttribute("appName", "Volando UY Backend");
        request.setAttribute("rutas", listaRuta);
        request.setAttribute("categorias", s.getCategorias());
        
        // Forward to JSP page
        request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
    }
}
