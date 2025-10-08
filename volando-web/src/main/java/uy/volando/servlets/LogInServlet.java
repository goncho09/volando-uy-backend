package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uy.volando.TypeAdapterDate;
import uy.volando.TypeAdapterTime;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@WebServlet (name = "LogInServlet", urlPatterns = {"/login","/signin"})
public class LogInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ISistema sistema = Factory.getSistema();
        List<DtUsuario> usuarios = sistema.listarUsuarios();

        Gson gson = new GsonBuilder().
                registerTypeAdapter(LocalTime.class,new TypeAdapterTime())
                .registerTypeAdapter(LocalDate.class,new TypeAdapterDate())
                .create();

        String usuariosJson = gson.toJson(usuarios);


        request.setAttribute("usuarios", usuariosJson);

        request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
    }
}
