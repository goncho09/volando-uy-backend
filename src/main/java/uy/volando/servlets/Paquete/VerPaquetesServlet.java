package uy.volando.servlets.Paquete;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtPaquete;
import com.app.datatypes.DtUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet (name = "VerPaquetesServlet", urlPatterns = {"/paquete/ver"})
public class VerPaquetesServlet extends HttpServlet {

    ISistema sistema = Factory.getSistema();

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

        try {
            String tipoUsuario = session.getAttribute("usuarioTipo").toString();
            String nickname = (String) session.getAttribute("usuarioNickname");
            List<DtPaquete> paqueteList = null;
            if (tipoUsuario.equals("aerolinea")) {
                paqueteList = sistema.listarPaquetesAerolinea(nickname);
            }else{
                paqueteList = sistema.listarPaquetesCliente(nickname);
            }
            request.setAttribute("paquetes", paqueteList);

            request.getRequestDispatcher("/WEB-INF/jsp/paquete/ver.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al obtener la lista de paquetes: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }

    }

}
