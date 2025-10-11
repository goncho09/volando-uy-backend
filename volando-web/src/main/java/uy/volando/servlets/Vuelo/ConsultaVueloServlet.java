package uy.volando.servlets.Vuelo;

import com.app.clases.Factory;
import com.app.clases.Sistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtRuta;
import com.app.datatypes.DtVuelo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ConsultaVueloServlet", urlPatterns = {"/vuelo/consulta"})
public class ConsultaVueloServlet extends HttpServlet {
    Sistema sistema = (Sistema) Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String idVuelo = request.getParameter("nombre");
            DtVuelo vuelo = sistema.getVuelo(idVuelo);
            DtRuta ruta = vuelo.getRutaDeVuelo().getDatos();

            request.setAttribute("ruta", ruta);
            request.setAttribute("vuelo", vuelo);

            request.getRequestDispatcher("/WEB-INF/jsp/vuelo/consulta.jsp").forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("error", "Error al cargar la consulta de vuelos");
            request.getRequestDispatcher("/WEB-INF/jsp/vuelo/consulta.jsp").forward(request, response);
            System.err.println("Error al obtener parametros: " + ex.getMessage());
        }
    }
}