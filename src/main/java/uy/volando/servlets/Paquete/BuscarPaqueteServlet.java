package uy.volando.servlets.Paquete;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.RutaEnPaquete;
import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtRutaEnPaquete;
import com.app.enums.EstadoRuta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

@WebServlet (name = "BuscarPaqueteServlet", urlPatterns = {"/paquete/buscar"})
public class BuscarPaqueteServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            List<DtPaquete> listaPaquete = sistema.listarPaquetesNoComprados();

            for(DtPaquete p : listaPaquete){
                List<DtRutaEnPaquete> listaRutasPaquete = p.getRutaEnPaquete();
                listaRutasPaquete.removeIf(rutaEnPaquete -> rutaEnPaquete.getRutaDeVuelo().getEstado() != EstadoRuta.APROBADA);
            }

            listaPaquete.removeIf(paquete -> paquete.getRutaEnPaquete() == null || paquete.getRutaEnPaquete().isEmpty());
            request.setAttribute("paquetes", listaPaquete);

            request.getRequestDispatcher("/WEB-INF/jsp/paquete/buscar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
