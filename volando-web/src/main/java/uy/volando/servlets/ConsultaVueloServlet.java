package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.Sistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtRuta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ConsultaVueloServlet", urlPatterns = {"/consulta-vuelos"})
public class ConsultaVueloServlet extends HttpServlet {
    Sistema sistema = (Sistema) Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String aerolineaId = request.getParameter("aerolineaSeleccionada");

        try {
            List<DtAerolinea> aerolineas = sistema.listarAerolineas();
            request.setAttribute("aerolineas", aerolineas);
            request.setAttribute("appName", "Volando UY - Consulta de Vuelos");

            // Cargar sus rutas
            if (aerolineaId != null && !aerolineaId.isEmpty()) {
                System.out.println("Cargando rutas para aerolínea: " + aerolineaId);

                DtAerolinea aerolinea = new DtAerolinea();
                aerolinea.setNickname(aerolineaId);
                List<DtRuta> rutas = sistema.listarRutasDeVuelo(aerolinea);

                System.out.println("Rutas encontradas: " + rutas.size());
                request.setAttribute("rutasAerolinea", rutas);
                request.setAttribute("aerolineaSeleccionada", aerolineaId);
            }

            request.getRequestDispatcher("/WEB-INF/jsp/consultaVuelo/consulta-vuelos.jsp").forward(request, response);

        } catch (Exception ex) {
            System.err.println("Error en ConsultaVueloServlet: " + ex.getMessage());
            request.setAttribute("error", "Error al cargar la consulta de vuelos");
            request.getRequestDispatcher("/WEB-INF/jsp/consultaVuelo/consulta-vuelos.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}