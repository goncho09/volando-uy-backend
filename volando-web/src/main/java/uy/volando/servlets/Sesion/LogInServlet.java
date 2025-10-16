package uy.volando.servlets.Sesion;

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

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioNickname") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioNickname") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            String name = request.getParameter("name");
            String password = request.getParameter("password");

            if (sistema.validarUsuario(name, password)) {
                sistema.elegirUsuario(name);
                DtUsuario usuario = sistema.getUsuarioSeleccionado();

                // Crea sesión aquí, solo si válido
                session = request.getSession(true);
                System.out.println(">>> LogIn doPost: Sesión creada para " + name);  // Debug temporal

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
                if (usuario instanceof DtCliente) {
                    session.setAttribute("usuarioTipo", "cliente");
                } else if (usuario instanceof DtAerolinea) {
                    session.setAttribute("usuarioTipo", "aerolinea");
                } else {
                    // Error de tipo: Invalida y forward
                    if (session != null) session.invalidate();  // Safe check
                    request.setAttribute("error", "Ha ocurrido un error.");
                    request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
                    return;
                }

                // <-- CORREGIDO: Setear AQUÍ, solo si válido y con el objeto completo
                session.setAttribute("usuario", usuario);
                System.out.println(">>> LogIn doPost: Usuario seteado en sesión: " + usuario.getNickname());  // Debug temporal

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("OK");
            } else {
                // Login inválido: Escribe error y SALE (no continúa al setAttribute)
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Nombre de usuario o contraseña incorrectos");
                return;  // <-- AGREGADO: Evita NPE en setAttribute
            }
            // <-- QUITADO: La línea session.setAttribute("usuario", name); de aquí

        } catch (Exception ex) {
            System.out.println(">>> LogIn doPost: Excepción = " + ex.getMessage());  // Debug
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error interno: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}