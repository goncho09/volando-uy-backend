package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtVuelo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uy.volando.model.Vuelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        ISistema sistema = Factory.getSistema();
        List<DtVuelo> vuelos = sistema.listarVuelos();
        List<Vuelo> listaVuelos = new ArrayList<>();

        for(DtVuelo vuelo : vuelos){
            listaVuelos.add(new Vuelo(vuelo));
        }

        // Set attributes to be used in JSP
        request.setAttribute("message", "Welcome to Volando UY!");
        request.setAttribute("appName", "Volando UY Backend");
        request.setAttribute("vuelos", listaVuelos);
        
        // Forward to JSP page
        request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
