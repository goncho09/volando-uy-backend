package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtUsuario;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@MultipartConfig
@WebServlet(name = "RegistrarUsuario", urlPatterns = {"/register","/signup","/registrar"})
public class RegistrarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/signup/registrarUsuario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try {
            ISistema sistema = Factory.getSistema();

            String nickname = request.getParameter("nickname");

            if(sistema.existeUsuarioNickname(nickname)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Ya existe un usuario con ese nickname.");
                return;
            }

            String email = request.getParameter("email");

            if(sistema.existeUsuarioEmail(email)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Ya existe un usuario con ese email.");
                return;
            }

            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");

            if (!password.equals(confirmPassword)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Las contraseñas no coinciden.");
                return;
            }

            String fotoPerfil = request.getParameter("image");
            String nombre = request.getParameter("name");
            String tipoUsuario = request.getParameter("role");

            response.setStatus(HttpServletResponse.SC_OK);

            HttpSession session = request.getSession(true);

            session.setAttribute("datosUsuario",new DtUsuario(nickname,email,nombre,password,fotoPerfil));
            session.setAttribute("tipoUsuario",tipoUsuario);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
        }
    }
}

