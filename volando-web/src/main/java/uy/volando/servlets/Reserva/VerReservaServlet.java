package uy.volando.servlets.Reserva;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtCliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet (name = "VerReservaServlet", urlPatterns = {"/reservas/ver"})
public class VerReservaServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Debés iniciar sesión");
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }
        if (request.getSession(false).getAttribute("usuarioTipo") == null || request.getSession(false).getAttribute("usuarioNickname") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Debés iniciar sesión");
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        try{
            String usuarioTipo = (String) request.getSession().getAttribute("usuarioTipo");
            String nicknameCliente = (String) request.getSession().getAttribute("usuarioNickname");

            if (usuarioTipo.equals("cliente")) {
                DtCliente cliente = sistema.getCliente(nicknameCliente);
                request.setAttribute("reservas", sistema.listarReservas(cliente));
            }
            else{
                DtAerolinea aerolinea = sistema.getAerolinea(nicknameCliente);
                request.setAttribute("reservas", sistema.listarReservas(aerolinea));
            }

            request.getRequestDispatcher("/WEB-INF/jsp/reservas/ver.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al obtener la lista de paquetes: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

    }
}
