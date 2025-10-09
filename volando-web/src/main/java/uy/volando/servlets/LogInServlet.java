package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;



@WebServlet (name = "LogInServlet", urlPatterns = {"/login","/signin"})
public class LogInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ISistema sistema = Factory.getSistema();

            String name = request.getParameter("name");
            String password = request.getParameter("password");


            if(sistema.validarUsuario(name,password)){
                sistema.elegirUsuario(name);
                DtUsuario usuario = sistema.getUsuarioSeleccionado();
                HttpSession session = request.getSession(true);
                session.setAttribute("usuarioNickname", usuario.getNickname());
                session.setAttribute("usuarioImagen", usuario.getUrlImage());
                sistema.borrarUsuarioSeleccionado();
                response.sendRedirect(request.getContextPath() + "/home");

            } else {
                request.setAttribute("error", "Nombre de usuario o contraseña incorrectos");
                request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }


}
