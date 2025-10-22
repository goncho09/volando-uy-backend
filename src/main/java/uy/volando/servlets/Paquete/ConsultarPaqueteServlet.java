package uy.volando.servlets.Paquete;

import com.app.clases.*;
import com.app.datatypes.DtCliente;
import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtRuta;
import com.app.datatypes.DtRutaEnPaquete;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "ConsultarPaqueteServlet", urlPatterns = {"/paquete/consulta"})
public class ConsultarPaqueteServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            String usuarioNick = request.getSession(false).getAttribute("usuarioNickname").toString();
            String nombre = request.getParameter("nombre");

            DtPaquete paquete = sistema.getPaquete(nombre);

            if (paquete == null) {request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);}

            List<DtRutaEnPaquete> rutaEnPaqueteList = paquete.getRutaEnPaquete();

            if(rutaEnPaqueteList == null || rutaEnPaqueteList.isEmpty()){request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);}

            String basePath = request.getServletContext().getRealPath("/pictures/rutas");
            String contextPath = request.getContextPath();

            for (DtRutaEnPaquete rp : rutaEnPaqueteList) {
                DtRuta rutaPaquete = rp.getRutaDeVuelo();
                String urlImagen = rutaPaquete.getUrlImagen();
                File rutaImg = null;

                if (urlImagen != null && !urlImagen.isEmpty()) {
                    rutaImg = new File(basePath, urlImagen);
                }

                if (urlImagen == null || urlImagen.isEmpty() || !rutaImg.exists()) {
                    rutaPaquete.setUrlImagen(contextPath + "/assets/rutaDefault.png");
                } else {
                    rutaPaquete.setUrlImagen(contextPath + "/pictures/rutas/" + urlImagen);
                }
            }

            boolean comprado = sistema.paqueteComprado(paquete);

            request.setAttribute("paquete", paquete);
            request.setAttribute("comprado", comprado);

            request.getRequestDispatcher("/WEB-INF/jsp/paquete/consulta.jsp").forward(request, response);
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}

