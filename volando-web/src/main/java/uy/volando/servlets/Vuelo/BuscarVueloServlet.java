package uy.volando.servlets.Vuelo;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtRuta;
import com.app.datatypes.DtVuelo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet (name = "BuscarVueloServlet", urlPatterns = {"/vuelo/buscar"})
public class BuscarVueloServlet extends HttpServlet {
    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List <DtAerolinea> aerolineas = sistema.listarAerolineas();
            aerolineas.removeIf(aerolinea -> aerolinea.listarRutasDeVuelo().isEmpty());
            request.setAttribute("aerolineas",aerolineas);

            String idAerolinea = request.getParameter("aerolinea");
            String idRuta = request.getParameter("ruta");

            if (idAerolinea != null && idRuta == null) {
                DtAerolinea aerolinea = sistema.getAerolinea(idAerolinea);
                request.setAttribute("rutas",aerolinea.listarRutasDeVuelo());
                request.setAttribute("aerolineaId", idAerolinea);
            }

            if (idRuta != null && idAerolinea != null) {
                DtAerolinea aerolinea = sistema.getAerolinea(idAerolinea);

                request.setAttribute("aerolineaId", idAerolinea);
                request.setAttribute("rutaId", idRuta);
                request.setAttribute("rutas", aerolinea.listarRutasDeVuelo());
                request.setAttribute("vuelos", sistema.listarVuelos(idRuta));
            }

            request.getRequestDispatcher("/WEB-INF/jsp/vuelo/buscar.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error en BuscarVueloServlet: " + e.getMessage());
            request.setAttribute("error", "Error al cargar la búsqueda de vuelos");
            request.getRequestDispatcher("/WEB-INF/jsp/vuelo/buscar.jsp").forward(request, response);
        }
    }

}
