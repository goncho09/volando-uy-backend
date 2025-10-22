package uy.volando.servlets.Paquete;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtPaquete;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet (name = "CrearPaqueteServlet", urlPatterns = {"/paquete/crear"})
public class CrearPaqueteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        if(session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null ){
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        if(!session.getAttribute("usuarioTipo").equals("aerolinea")){
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/paquete/crear.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ISistema sistema = Factory.getSistema();

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String validezStr = request.getParameter("validez");
            String descuentoStr = request.getParameter("descuento");

            if (nombre == null || descripcion == null || validezStr == null || descuentoStr == null) {
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


            if (descripcion.isEmpty() || validezDias <= 0 || descuento < 0 || descuento > 100) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Datos inválidos. Por favor, verifique e intente nuevamente.");
                return;
            }

            sistema.altaPaquete(new DtPaquete(nombre, descripcion, validezDias, descuento, 0));

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Paquete creado con éxito");

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }


    }

}
