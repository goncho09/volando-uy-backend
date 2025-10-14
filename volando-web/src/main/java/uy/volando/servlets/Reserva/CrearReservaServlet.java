package uy.volando.servlets.Reserva;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtCliente;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Crear", urlPatterns = {"/reserva/crear"})

public class CrearReservaServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/reservas/crearReserva.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws jakarta.servlet.ServletException, java.io.IOException {

        try {
            ISistema sistema = Factory.getSistema();

            //Datos del form
            String aerolinea = request.getParameter("aerolinea-select");
            String ruta = request.getParameter("ruta-select");
            String vuelo = request.getParameter("vuelo-select");
            String cantidad = request.getParameter("cantidad-pasajes");
            String tipoAsiento = request.getParameter("tipo-asiento");

            //obtener la sesion del usuario
            HttpSession session = request.getSession(false);
            if (session == null || !session.getAttribute("usuarioTipo").equals("cliente")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Debés iniciar sesión como cliente");
                return;
            }

            String nicknameCliente = (String) session.getAttribute("usuarioNickname");

            //buscar si existe el cliente
            DtCliente clienteLogueado = null;
            for (DtCliente c : sistema.listarClientes()) {
                if (c.getNickname().equals(nicknameCliente)) {
                    clienteLogueado = c;
                    break;
                }
            }

            if(clienteLogueado == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Cliente no encontrado");
                return;
            }

            if (aerolinea == null || vuelo == null || tipoAsiento == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Faltan datos obligatorios");
                return;
            }

            //aca buscar el vuelo

            // aca lo de los pasajeros

            //dar alta reserva
             //sistema.altaReserva(reserva, clienteLogueado, vueloSeleccionado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Reserva creada con éxito");

        }catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error del servidor: " + ex.getMessage());
        }

    }

}
