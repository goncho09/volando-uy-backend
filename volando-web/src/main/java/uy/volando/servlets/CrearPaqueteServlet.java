package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.Paquete;
import com.app.datatypes.DtPaquete;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "CrearPaqueteServlet", urlPatterns = {"/paquete/crear"})
public class CrearPaqueteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/paquete/crear.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        try {
            ISistema sistema = Factory.getSistema();

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String validezStr = request.getParameter("validez");
            String descuentoStr = request.getParameter("descuento");
            String precioStr = request.getParameter("precio");

            if (nombre == null || descripcion == null || validezStr == null || descuentoStr == null || precioStr == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Faltan campos obligatorios");
                return;
            }

            if (sistema.existePaquete(nombre)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Ya existe un paquete con ese nombre");
                return;
            }

            int validezDias = Integer.parseInt(validezStr);
            int descuento = Integer.parseInt(descuentoStr);
            float precio = Float.parseFloat(precioStr);

            if (descripcion.isEmpty() || validezDias <= 0 || descuento < 0 || descuento > 100 || precio <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Datos inválidos. Por favor, verifique e intente nuevamente.");
                return;
            }

            sistema.altaPaquete(new DtPaquete(nombre, descripcion, validezDias, descuento, precio));

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Paquete creado con éxito");

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }


    }

}
