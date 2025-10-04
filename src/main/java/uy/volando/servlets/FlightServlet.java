package uy.volando.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uy.volando.model.Flight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FlightServlet", urlPatterns = {"/flights"})
public class FlightServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Create sample flight data
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight("VU101", "Montevideo", "Buenos Aires", "08:00", "On Time"));
        flights.add(new Flight("VU202", "Montevideo", "Sao Paulo", "10:30", "Delayed"));
        flights.add(new Flight("VU303", "Montevideo", "Santiago", "14:15", "On Time"));
        flights.add(new Flight("VU404", "Montevideo", "Lima", "16:45", "Boarding"));
        
        // Set flights list as request attribute
        request.setAttribute("flights", flights);
        
        // Forward to JSP page
        request.getRequestDispatcher("/WEB-INF/jsp/flights.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
