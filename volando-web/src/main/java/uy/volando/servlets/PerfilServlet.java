package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "PerfilServlet", urlPatterns = {"/perfil"})
public class PerfilServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PerfilServlet.class.getName());
    private ISistema s = Factory.getSistema();  // Mueve aquí si es singleton, pero mejor init en doGet para lazy.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        String nickname = request.getParameter("nickname");  // Cambié a "nickname"; ajusta si es "nombre".
//        if (nickname == null || nickname.isEmpty()) {
//            // Fallback: Usa sesión si logueado.
//            nickname = (String) request.getSession().getAttribute("nicknameLogueado");  // Asume que guardas en login.
//            if (nickname == null) {
//                response.sendRedirect(request.getContextPath() + "/login");  // Redirige si no hay user.
//                return;
//            }
//        }

//        try {
//            DtUsuario usuario = s.getUsuario(nickname);
//            if (usuario == null) {
//                throw new Exception("Usuario no encontrado: " + nickname);
//            }
//
//            // Lógica de imagen
//            String basePath = request.getServletContext().getRealPath("/pictures/users");
//            String contextPath = request.getContextPath();
//            String urlImagen = usuario.getUrlImage();
//            File userImg = null;
//
//            if (urlImagen != null && !urlImagen.isEmpty()) {
//                userImg = new File(basePath, urlImagen);
//            }
//
//            if (urlImagen == null || urlImagen.isEmpty() || !userImg.exists()) {
//                usuario.setUrlImage(contextPath + "/assets/user-image.png");
//            } else {
//                usuario.setUrlImage(contextPath + "/pictures/users/" + urlImagen);
//            }

//            request.setAttribute("usuario", usuario);
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Error cargando perfil para " + nickname, e);
//            request.setAttribute("error", "Error al cargar perfil: " + e.getMessage());
//            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
//            return;
//        }


        // Forward to JSP page
        request.getRequestDispatcher("/WEB-INF/jsp/perfil/perfil.jsp").forward(request, response);
    }
}