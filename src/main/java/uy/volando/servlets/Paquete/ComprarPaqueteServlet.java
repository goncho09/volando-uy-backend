package uy.volando.servlets.Paquete;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.RutaEnPaquete;
import com.app.datatypes.DtCliente;
import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtRutaEnPaquete;
import com.app.enums.EstadoRuta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ComprarPaqueteServlet", urlPatterns = {"/paquete/comprar"})
public class ComprarPaqueteServlet extends HttpServlet {

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

        if (!session.getAttribute("usuarioTipo").equals("cliente")) {
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
            return;
        }

        try {
            List<DtPaquete> listaPaquete = sistema.listarPaquetesNoComprados();

            for(DtPaquete p : listaPaquete){
                List<DtRutaEnPaquete> listaRutasPaquete = p.getRutaEnPaquete();
                listaRutasPaquete.removeIf(rutaEnPaquete -> rutaEnPaquete.getRutaDeVuelo().getEstado() != EstadoRuta.APROBADA);
            }

            listaPaquete.removeIf(paquete -> paquete.getRutaEnPaquete() == null || paquete.getRutaEnPaquete().isEmpty());

            request.setAttribute("paquetes", listaPaquete);

            request.getRequestDispatcher("/WEB-INF/jsp/paquete/comprar.jsp").forward(request, response);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Error en ComprarPaqueteServlet (GET): " + ex.getMessage());
            response.getWriter().write("Error del servidor: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/401.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        try {
            String paqueteNombre = request.getParameter("paquete");
            System.out.println("Nombre paquete recibido: " + paqueteNombre);

            if (paqueteNombre == null || paqueteNombre.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Debe seleccionar un paquete para comprar.");
                return;
            }

            String nicknameCliente = (String) session.getAttribute("usuarioNickname");
            DtCliente clienteLogueado = sistema.getCliente(nicknameCliente);
            DtPaquete paqueteSelect = sistema.getPaquete(paqueteNombre);

            if (paqueteSelect == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("El paquete seleccionado no existe.");
                return;
            }

            sistema.compraPaquete(paqueteSelect, clienteLogueado);

            List<DtPaquete> paquetes = sistema.listarPaquetesNoComprados();
            request.setAttribute("paquetes", paquetes);

            request.setAttribute("mensaje", "Compra realizada con éxito");
            request.getRequestDispatcher("/WEB-INF/jsp/paquete/comprar.jsp").forward(request, response);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Error en ComprarPaqueteServlet (POST): " + ex.getMessage());
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }
    }
}
