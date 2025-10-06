package uy.volando.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uy.volando.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FlightServlet", urlPatterns = {"/flights"})
public class VueloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        LocalDate fecha = LocalDate.now();

        Ciudad ciudad1 = new Ciudad("Montevideo", "Uruguay","Aeropuerto Internacional de Carrasco", "La capital de Uruguay", "www.montevideo.gub.uy", fecha);
        Ciudad ciudad2 = new Ciudad("Buenos Aires", "Argentina","Aeropuerto Internacional Ministro Pistarini", "La capital de Argentina", "www.buenosaires.gob.ar", fecha);

        RutaDeVuelo rutaDeVuelo = new RutaDeVuelo("Ruta1", "Descripcion de la ruta 1", 130, 150.0f, 300.0f, 50.0f, null, "url_imagen", null, null, ciudad1,ciudad2);
        Vuelo vuelo = new Vuelo("Vuelo1", null, 130,15,20,"linkimagen", fecha, rutaDeVuelo,2);
        
        // Create sample flight data
        List<Vuelo> vuelos = new ArrayList<>();

        vuelos.add(vuelo);

        
        // Set flights list as request attribute
        request.setAttribute("flights", vuelos);
        
        // Forward to JSP page
        request.getRequestDispatcher("/WEB-INF/jsp/flights.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
