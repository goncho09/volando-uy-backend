package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtUsuario;
import com.app.datatypes.DtVuelo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uy.volando.model.Usuario;
import uy.volando.model.Vuelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet (name = "LogInServlet", urlPatterns = {"/login","/signin"})
public class LogInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ISistema sistema = Factory.getSistema();
        List<DtUsuario> usuarios = sistema.listarUsuarios();


        List<Usuario> listaUsuarios = new ArrayList<>();

        for(DtUsuario usuario : usuarios){
            listaUsuarios.add(new Usuario(usuario));
        }

        request.setAttribute("usuarios", listaUsuarios);

        request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
    }
}
