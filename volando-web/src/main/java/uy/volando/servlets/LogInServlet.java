package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtCliente;
import com.app.datatypes.DtUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;



@WebServlet (name = "LogInServlet", urlPatterns = {"/login","/signin"})
public class LogInServlet extends HttpServlet {
    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String name = request.getParameter("name");
            String password = request.getParameter("password");


            if(sistema.validarUsuario(name,password)){
                sistema.elegirUsuario(name);
                DtUsuario usuario = sistema.getUsuarioSeleccionado();
                HttpSession session = request.getSession(true);
                session.setAttribute("usuarioNickname", usuario.getNickname());

                String basePath = request.getServletContext().getRealPath("/pictures/users");
                String contextPath = request.getContextPath();

                String urlImagen = usuario.getUrlImage();
                File userImg = null;

                if (urlImagen != null && !urlImagen.isEmpty()) {
                    userImg = new File(basePath, urlImagen);
                }

                if (urlImagen == null || urlImagen.isEmpty() || !userImg.exists()) {
                    usuario.setUrlImage(contextPath + "/assets/userDefault.png");
                } else {
                    usuario.setUrlImage(contextPath + "/pictures/users/" + urlImagen);
                }

                session.setAttribute("usuarioImagen", usuario.getUrlImage());
                sistema.borrarUsuarioSeleccionado();
                if(usuario instanceof DtCliente){
                    session.setAttribute("usuarioTipo", "cliente");
                }else if(usuario instanceof DtAerolinea){
                    session.setAttribute("usuarioTipo", "aerolinea");
                }else{
                    session.invalidate();
                    request.setAttribute("error", "Ha ocurrido un error.");
                    request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
                }
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
