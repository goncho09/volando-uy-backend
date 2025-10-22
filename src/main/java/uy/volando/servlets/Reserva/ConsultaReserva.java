package uy.volando.servlets.Reserva;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtCliente;
import com.app.datatypes.DtVuelo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "ConsultaReserva", urlPatterns = {"/reservas/consulta"})
public class ConsultaReserva extends HttpServlet {
    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        if (session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        try{
            String nicknameCliente = (String) request.getSession().getAttribute("usuarioNickname");
            String usuarioTipo = (String) request.getSession().getAttribute("usuarioTipo");
            String fechaReserva = request.getParameter("fecha");
            LocalDate fechaReservaDate = LocalDate.parse(fechaReserva);
            DtVuelo vuelo = sistema.getVuelo(request.getParameter("vuelo"));

            if (usuarioTipo.equals("cliente")) {
                DtCliente cliente = sistema.getCliente(nicknameCliente);

                request.setAttribute("reserva", sistema.getReservaCliente(vuelo, cliente, fechaReservaDate));
            }
//            else{
////                DtAerolinea aerolinea = sistema.getAerolinea(nicknameCliente);
////                request.setAttribute("reservas", sistema.listarReservas(aerolinea));
//            }

            request.getRequestDispatcher("/WEB-INF/jsp/reservas/consulta.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e.getMessage());
            response.getWriter().write("Error al obtener la lista de reservas: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

    }
}




