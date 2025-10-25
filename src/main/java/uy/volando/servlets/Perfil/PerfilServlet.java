package uy.volando.servlets.Perfil;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PerfilServlet", urlPatterns = {"/perfil"})
public class PerfilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Fix: Declara usuarioTipo y nickname al inicio para scope global
        String usuarioTipo = (String) session.getAttribute("usuarioTipo");
        String nickname = (String) session.getAttribute("usuarioNickname");

        // Debug logs al inicio (después de session check)
        String existingImagen = (String) session.getAttribute("usuarioImagen");

        ISistema sistema = Factory.getSistema();
        // Recarga usuario si falta
        if (session.getAttribute("usuario") == null) {
            try {
                DtUsuario usuario = sistema.getUsuario(nickname);

                session.setAttribute("usuario", usuario);

                String basePath = request.getServletContext().getRealPath("/pictures/users");
                String contextPath = request.getContextPath();

                String urlImagen = usuario.getUrlImage();
                File userImg = null;

                if (urlImagen != null && !urlImagen.isEmpty()) {
                    userImg = new File(basePath, urlImagen);
                }

                System.out.println(usuario.getUrlImage());
                if (urlImagen == null || urlImagen.isEmpty() || !userImg.exists()) {
                    usuario.setUrlImage(contextPath + "/assets/userDefault.png");
                } else {
                    usuario.setUrlImage(contextPath + "/pictures/users/" + urlImagen);
                }

                session.setAttribute("usuarioImagen", usuario.getUrlImage());

            } catch (Exception e) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        if (session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // PRIORIDAD: Preserva/actualiza imagen de sesión vs BD (siempre, post-recarga)
            DtUsuario usuario = (DtUsuario) session.getAttribute("usuario");
            if ("cliente".equals(usuarioTipo)) {
                DtCliente cliente = sistema.getCliente(nickname);
                request.setAttribute("cliente", cliente);

                String currentSessionImagen = (String) session.getAttribute("usuarioImagen");
                String relativeBd = normalizarARelativePath(cliente.getUrlImage(), request.getContextPath());
                String basePath = request.getServletContext().getRealPath("/pictures/users");
                String fullPathBd;
                if (relativeBd == null || relativeBd.isEmpty() || !new File(basePath, extraerFilename(relativeBd)).exists()) {
                    fullPathBd = request.getContextPath() + "/assets/userDefault.png";
                } else {
                    fullPathBd = request.getContextPath() + relativeBd;
                }

                if (currentSessionImagen == null || !currentSessionImagen.startsWith(request.getContextPath())) {
                    session.setAttribute("usuarioImagen", fullPathBd);
                    // Sync con objeto usuario
                    usuario.setUrlImage(relativeBd);
                    session.setAttribute("usuario", usuario);
                } else {
                    // Sync con objeto usuario si difiere (usa relative de sesión)
                    String relativeSession = normalizarARelativePath(currentSessionImagen, request.getContextPath());
                    if (!relativeSession.equals(usuario.getUrlImage())) {
                        usuario.setUrlImage(relativeSession);
                        session.setAttribute("usuario", usuario);
                    }
                }
            } else if ("aerolinea".equals(usuarioTipo)) {
                DtAerolinea aerolinea = sistema.getAerolinea(nickname);
                request.setAttribute("aerolinea", aerolinea);

                String currentSessionImagen = (String) session.getAttribute("usuarioImagen");
                String relativeBd = normalizarARelativePath(aerolinea.getUrlImage(), request.getContextPath());
                String basePath = request.getServletContext().getRealPath("/pictures/users");
                String fullPathBd;
                if (relativeBd == null || relativeBd.isEmpty() || !new File(basePath, extraerFilename(relativeBd)).exists()) {
                    fullPathBd = request.getContextPath() + "/assets/userDefault.png";
                } else {
                    fullPathBd = request.getContextPath() + relativeBd;
                }

                if (currentSessionImagen == null || !currentSessionImagen.startsWith(request.getContextPath())) {
                    session.setAttribute("usuarioImagen", fullPathBd);
                    // Sync con objeto usuario
                    usuario.setUrlImage(relativeBd);
                    session.setAttribute("usuario", usuario);
                } else {
                    // Sync con objeto usuario si difiere
                    String relativeSession = normalizarARelativePath(currentSessionImagen, request.getContextPath());
                    if (!relativeSession.equals(usuario.getUrlImage())) {
                        usuario.setUrlImage(relativeSession);
                        session.setAttribute("usuario", usuario);
                    }
                }
            }

            // Carga paquetes
            if ("cliente".equals(usuarioTipo)) {
                DtCliente cliente = (DtCliente) request.getAttribute("cliente");
                if (cliente == null) cliente = sistema.getCliente(nickname);
                List<DtPaquete> paquetes = sistema.listarPaquetesCliente(cliente.getNickname());
                request.setAttribute("paquetes", paquetes);
            }

            // Carga reservas
            if("cliente".equals(usuarioTipo)) {
                DtCliente cliente = (DtCliente) request.getAttribute("cliente");
                if (cliente == null) cliente = sistema.getCliente(nickname);
                List<DtReserva> reservas = sistema.listarReservasCliente(cliente.getNickname());
                request.setAttribute("reservas", reservas);
            }

            // Maneja params de redirect (error/success)
            String error = request.getParameter("error");
            if (error != null) request.setAttribute("error", error);
            String success = request.getParameter("success");
            if (success != null) request.setAttribute("success", success);


            request.getRequestDispatcher("/WEB-INF/jsp/perfil/perfil.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(">>> PerfilServlet: Error = " + e.getMessage());
            request.setAttribute("error", "Error al cargar perfil: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/perfil/perfil.jsp").forward(request, response);
        }
    }

    // Helper: Normaliza path a relative (quita contextPath si está)
    private String normalizarARelativePath(String path, String contextPath) {
        if (path == null) return null;
        path = path.trim();
        if (path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        if (path.startsWith("/")) {
            // Ya relative como "/pictures/users/file.jpg"
            return path;
        } else {
            // Si es solo filename, asume /pictures/users/
            return "/pictures/users/" + path;
        }
    }

    // Helper: Extrae solo filename para File check (quita dir)
    private String extraerFilename(String relativePath) {
        if (relativePath == null) return null;
        String filename = relativePath;
        if (filename.contains("/")) {
            filename = filename.substring(filename.lastIndexOf("/") + 1);
        }
        return filename;
    }
}