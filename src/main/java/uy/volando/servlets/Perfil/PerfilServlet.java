package uy.volando.servlets.Perfil;

import com.app.enums.TipoDocumento;
import com.app.enums.TipoImagen;
import com.app.utils.AuxiliarFunctions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.*;


@MultipartConfig
        (
                fileSizeThreshold = 1024 * 1024, // 1 MB
                maxFileSize = 1024 * 1024 * 10,      //
                maxRequestSize = 1024 * 1024 * 15    // 15 MB
        )
@WebServlet(name = "PerfilServlet", urlPatterns = {"/perfil"})
public class PerfilServlet extends HttpServlet {
    ISistema sistema = Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String usuarioTipo = (String) session.getAttribute("usuarioTipo");
        String nickname = (String) session.getAttribute("usuarioNickname");


        try {
            DtUsuario usuario = sistema.getUsuario(nickname);

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
            session.setAttribute("usuario", usuario);

            if (usuarioTipo.equals("cliente")) {
                request.setAttribute("cliente", sistema.getCliente(nickname));
            } else if (usuarioTipo.equals("aerolinea")) {
                request.setAttribute("aerolinea", sistema.getAerolinea(nickname));
            }

            request.getRequestDispatcher("/WEB-INF/jsp/perfil/perfil.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(">>> PerfilServlet: Error = " + e.getMessage());
            request.setAttribute("error", "Error al cargar perfil: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/perfil/perfil.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            String usuarioTipo = (String) session.getAttribute("usuarioTipo");
            String nickname = (String) session.getAttribute("usuarioNickname");
            DtUsuario usuario = sistema.getUsuario(nickname);

            Part imagePart = request.getPart("image");
            String nombre = request.getParameter("nombre");

            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            File tempFile = File.createTempFile("upload-", "-" + fileName);

            try (InputStream input = imagePart.getInputStream()) {
                Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            File imagenGuardada = AuxiliarFunctions.guardarImagen(tempFile, TipoImagen.USUARIO);

            String fotoPerfil = imagenGuardada.getName();
            usuario.setNombre(nombre);
            usuario.setUrlImage(fotoPerfil);

            if (usuarioTipo.equals("cliente")) {

                String apellido = request.getParameter("apellido");
                String fechaNacimiento = request.getParameter("fechaNacimiento");
                String nacionalidad = request.getParameter("nacionalidad");
                String tipoDocumento = request.getParameter("tipoDocumento");
                String numeroDocumento = request.getParameter("numeroDocumento");

                LocalDate fechaNacDate = null;
                try {
                    fechaNacDate = LocalDate.parse(fechaNacimiento);
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("ERROR: Fecha de nacimiento inválida");
                    request.setAttribute("error", "Fecha de nacimiento inválida");
                    System.out.println(">>> PerfilServlet POST: Error al setear datos usuario = " + e.getMessage());
                }

                TipoDocumento tipoDocumentoEnum = null;
                int numDoc = 0;
                try {
                    tipoDocumentoEnum = TipoDocumento.valueOf(tipoDocumento);
                    numDoc = Integer.parseInt(numeroDocumento);
                } catch (Exception e) {
                    request.setAttribute("error", "Tipo o número de documento inválido");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("ERROR: Tipo o número de documento inválido");
                    System.out.println(">>> PerfilServlet POST: Error al setear datos usuario = " + e.getMessage());
                }
                sistema.modificarCliente(new DtCliente(usuario, apellido, fechaNacDate, nacionalidad, tipoDocumentoEnum, numDoc));
            } else if (usuarioTipo.equals("aerolinea")) {
                String descripcion = request.getParameter("descripcion");
                String linkWeb = request.getParameter("linkWeb");
                sistema.modificarAerolinea(new DtAerolinea(usuario, descripcion, linkWeb));
            }

            // Actualiza session usuario
            DtUsuario updatedUsuario = sistema.getUsuario(nickname);
            session.setAttribute("usuario", updatedUsuario);
            session.setAttribute("usuarioImagen", updatedUsuario.getUrlImage());
            request.setAttribute("success", "Perfil actualizado correctamente");

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("OK");
            request.getRequestDispatcher("/WEB-INF/jsp/perfil/perfil.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println(">>> PerfilServlet POST: Error = " + e.getMessage());
            request.setAttribute("error", "Error del servidor");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", "Error al actualizar perfil");
            request.getRequestDispatcher("/WEB-INF/jsp/perfil/perfil.jsp").forward(request, response);
        }

    }

}