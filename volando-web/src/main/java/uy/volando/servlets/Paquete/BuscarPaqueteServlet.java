package uy.volando.servlets.Paquete;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtPaquete;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet (name = "BuscarPaqueteServlet", urlPatterns = {"/paquete/buscar"})
public class BuscarPaqueteServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            List<DtPaquete> paquetes = sistema.listarPaquetes();

            request.setAttribute("paquetes", paquetes);

            request.getRequestDispatcher("/WEB-INF/jsp/paquete/buscar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
