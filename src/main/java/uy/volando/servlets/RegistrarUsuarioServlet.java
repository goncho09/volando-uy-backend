package uy.volando.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;


@WebServlet(name = "RegistrarUsuario", urlPatterns = {"/register","/signup"})
public class RegistrarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/signup/registrarUsuario.jsp").forward(request, response);
    }
}

