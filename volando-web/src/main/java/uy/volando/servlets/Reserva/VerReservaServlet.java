package uy.volando.servlets.Reserva;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtCliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet (name = "VerReservasServlet", urlPatterns = {"/reservas/ver"})
public class VerReservaServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            // Se rompe este if
            if (request.getSession(false) == null || !request.getSession(false).getAttribute("usuarioTipo").equals("cliente")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Debés iniciar sesión como cliente");
                request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
                return;
            }
            String nicknameCliente = (String) request.getSession().getAttribute("usuarioNickname");
            DtCliente cliente = sistema.getCliente(nicknameCliente);
            request.setAttribute("reservas", sistema.listarReservas(cliente));

            request.getRequestDispatcher("/WEB-INF/jsp/reservas/ver.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al obtener la lista de paquetes: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

    }
}
