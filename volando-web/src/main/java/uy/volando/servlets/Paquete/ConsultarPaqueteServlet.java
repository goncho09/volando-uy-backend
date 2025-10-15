package uy.volando.servlets.Paquete;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.RutaEnPaquete;
import com.app.datatypes.DtPaquete;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet (name = "ConsultarPaqueteServlet", urlPatterns = {"/paquete/consulta"})
public class ConsultarPaqueteServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            String nombre = request.getParameter("nombre");

            DtPaquete paquete = sistema.getPaquete(nombre);
            // Deberia estar??
            if (paquete == null) request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);

            String basePath = request.getServletContext().getRealPath("/pictures/rutas");
            String contextPath = request.getContextPath();

            for (RutaEnPaquete rp : paquete.getRutaEnPaquete()) {
                String urlImagen = rp.getRutaDeVuelo().getUrlImagen();
                File rutaImg = null;

                if (urlImagen != null && !urlImagen.isEmpty()) {
                    rutaImg = new File(basePath, urlImagen);
                }

                if (urlImagen == null || urlImagen.isEmpty() || !rutaImg.exists()) {
                    rp.getRutaDeVuelo().setUrlImagen(contextPath + "/assets/rutaDefault.png");
                } else {
                    rp.getRutaDeVuelo().setUrlImagen(contextPath + "/pictures/rutas/" + urlImagen);
                }
            }

            request.setAttribute("paquete", paquete);
            request.getRequestDispatcher("/WEB-INF/jsp/paquete/consulta.jsp").forward(request, response);
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}

