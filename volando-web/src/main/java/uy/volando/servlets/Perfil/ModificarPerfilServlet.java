package uy.volando.servlets.Perfil;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.*;
import com.app.enums.TipoDocumento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@MultipartConfig(
        location = "",
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 1024 * 1024 * 5,    // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
@WebServlet(name = "ModificarPerfilServlet", urlPatterns = {"/modificar-perfil"})
public class ModificarPerfilServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "/pictures/users";  // Path relativo para URL

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(">>> ModificarPerfilServlet doPost llamado");

        request.setCharacterEncoding("UTF-8");  // Fix: Asegura UTF-8 para params

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioTipo") == null || session.getAttribute("usuarioNickname") == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=sesion_invalida");
            return;
        }

        String usuarioTipo = (String) session.getAttribute("usuarioTipo");
        String nickname = (String) session.getAttribute("usuarioNickname");
        ISistema sistema = Factory.getSistema();

        String success = null;
        String error = null;

        try {
            // Obtén DtUsuario actual de sesión
            DtUsuario usuarioActual = (DtUsuario) session.getAttribute("usuario");
            if (usuarioActual == null) {
                sistema.elegirUsuario(nickname);
                usuarioActual = sistema.getUsuarioSeleccionado();
                sistema.borrarUsuarioSeleccionado();
            }

            // Manejo de imagen primero (común)
            Part filePart = null;
            try {
                filePart = request.getPart("imagen");
                System.out.println(">>> FilePart obtenido: " + (filePart != null ? "OK (size=" + filePart.getSize() + ")" : "NULL"));  // Debug mejorado
            } catch (Exception e) {
                System.out.println(">>> Error getPart imagen: " + e.getMessage());
                e.printStackTrace();
                error = "Error procesando imagen: " + e.getMessage();
            }
            String nuevaUrlImagen = null;
            if (filePart != null && filePart.getSize() > 0) {
                String submittedFileName = filePart.getSubmittedFileName();
                String extension = "jpg";  // Default
                if (submittedFileName != null && submittedFileName.contains(".")) {
                    extension = submittedFileName.substring(submittedFileName.lastIndexOf(".") + 1).toLowerCase();
                    if (!extension.matches("(?i)(jpg|jpeg|png|gif)")) {  // Valida mejor (case-insensitive)
                        throw new IOException("Tipo de imagen no permitido: " + extension);
                    }
                }
                String fileName = "user_" + UUID.randomUUID().toString() + "." + extension;
                String uploadPath = getServletContext().getRealPath(UPLOAD_DIR);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    if (!uploadDir.mkdirs()) {
                        throw new IOException("No se pudo crear directorio: " + uploadPath);
                    }
                }
                Path filePath = Paths.get(uploadPath, fileName);
                try (var inputStream = filePart.getInputStream()) {
                    Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
                nuevaUrlImagen = request.getContextPath() + UPLOAD_DIR + "/" + fileName;  // Path absoluto para JSP
                System.out.println(">>> Imagen subida exitosa: diskPath=" + filePath + ", urlParaJSP=" + nuevaUrlImagen);  // Debug
            } else {
                System.out.println(">>> No hay imagen nueva (filePart null o vacío)");  // Debug
            }

            // Actualiza campos comunes (nombre)
            String nuevoNombre = request.getParameter("nombre");
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                usuarioActual.setNombre(nuevoNombre.trim());
            }

            DtUsuario usuarioRecargado = null;  // Para setear en sesión al final

            if ("cliente".equals(usuarioTipo)) {
                // Prepara params con validaciones
                String apellido = request.getParameter("apellido");
                String fechaNacStr = request.getParameter("fechaNacimiento");
                String nacionalidad = request.getParameter("nacionalidad");
                String tipoDocStr = request.getParameter("tipoDocumento");
                String numDocStr = request.getParameter("numeroDocumento");

                if (apellido == null || apellido.trim().isEmpty() || fechaNacStr == null || nacionalidad == null ||
                        tipoDocStr == null || numDocStr == null) {
                    throw new IllegalArgumentException("Faltan campos requeridos para cliente");
                }

                // Crea DtCliente actualizado (usa valores actuales si no hay nuevos, pero como es form, asume siempre)
                DtCliente clienteActual = new DtCliente(
                        usuarioActual.getNickname(),
                        usuarioActual.getNombre(),  // Ya actualizado arriba
                        usuarioActual.getEmail(),
                        apellido.trim(),
                        LocalDate.parse(fechaNacStr),  // Asume válido del JS
                        nacionalidad.trim(),
                        TipoDocumento.valueOf(tipoDocStr),
                        Integer.parseInt(numDocStr)
                );
                // Copia comprasPaquetes si es necesario (de original)
                if (usuarioActual instanceof DtCliente) {
                    clienteActual.setComprasPaquetes(((DtCliente) usuarioActual).getComprasPaquetes());
                }

                // Actualiza imagen en el objeto (usando setter de DtUsuario)
                if (nuevaUrlImagen != null) {
                    clienteActual.setUrlImage(nuevaUrlImagen);
                    System.out.println(">>> Imagen seteada en clienteActual: " + nuevaUrlImagen);
                }

                // Actualiza en sistema (incluye imagen)
                sistema.modificarCliente(clienteActual);

                // Recarga para verificar BD
                usuarioRecargado = sistema.getCliente(nickname);
                System.out.println(">>> Cliente recargado post-mod: nombre='" + usuarioRecargado.getNombre() +
                        "', imagenBD='" + usuarioRecargado.getUrlImage() + "'");  // Debug clave: ¿coincide con nueva?

                if (nuevaUrlImagen != null && !nuevaUrlImagen.equals(usuarioRecargado.getUrlImage())) {
                    System.out.println(">>> WARNING: Imagen NO persistió en BD! Usando sesión como fallback.");
                }

            } else if ("aerolinea".equals(usuarioTipo)) {
                // Prepara params
                String descripcion = request.getParameter("descripcion");
                String linkWeb = request.getParameter("linkWeb");

                // Crea DtAerolinea actualizado
                DtAerolinea aerolineaActual = new DtAerolinea(
                        usuarioActual.getNickname(),
                        usuarioActual.getNombre(),
                        usuarioActual.getEmail(),
                        usuarioActual.getPassword(),
                        usuarioActual.getUrlImage(),  // Temporal, se actualizará abajo
                        descripcion != null ? descripcion.trim() : "",
                        linkWeb != null ? linkWeb.trim() : ""
                );
                // Copia rutasDeVuelo
                if (usuarioActual instanceof DtAerolinea) {
                    aerolineaActual.setRutasDeVuelo(((DtAerolinea) usuarioActual).getRutasDeVuelo());
                }

                // Actualiza imagen en el objeto (usando setter de DtUsuario)
                if (nuevaUrlImagen != null) {
                    aerolineaActual.setUrlImage(nuevaUrlImagen);
                    System.out.println(">>> Imagen seteada en aerolineaActual: " + nuevaUrlImagen);
                }

                // Actualiza en sistema (incluye imagen)
                sistema.modificarAerolinea(aerolineaActual);

                // Recarga para verificar
                usuarioRecargado = sistema.getAerolinea(nickname);
                System.out.println(">>> Aerolinea recargada post-mod: nombre='" + usuarioRecargado.getNombre() +
                        "', imagenBD='" + usuarioRecargado.getUrlImage() + "'");  // Debug clave

                if (nuevaUrlImagen != null && !nuevaUrlImagen.equals(usuarioRecargado.getUrlImage())) {
                    System.out.println(">>> WARNING: Imagen NO persistió en BD! Usando sesión como fallback.");
                }
            } else {
                throw new IllegalArgumentException("Tipo de usuario inválido: " + usuarioTipo);
            }

            // Actualiza sesión con recargado (incluye cambios de BD)
            if (usuarioRecargado != null) {
                session.setAttribute("usuario", usuarioRecargado);
            }
            // Siempre prioriza nueva imagen en sesión (fallback si BD falla)
            if (nuevaUrlImagen != null) {
                session.setAttribute("usuarioImagen", nuevaUrlImagen);
                session.setAttribute("ultimoUpdateImagen", System.currentTimeMillis());  // Timestamp
                System.out.println(">>> Timestamp update seteado: " + session.getAttribute("ultimoUpdateImagen"));
            }

            success = "Perfil actualizado correctamente";
            System.out.println(">>> Perfil actualizado para: " + nickname + " (tipo: " + usuarioTipo + ")");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(">>> Error modificando perfil: " + e.getMessage());
            error = "Error al actualizar perfil: " + e.getMessage();
        }

        // Redirect con mensajes (JSP los muestra)
        String redirectUrl = request.getContextPath() + "/perfil";
        if (success != null) {
            redirectUrl += "?success=" + java.net.URLEncoder.encode(success, "UTF-8");
        } else if (error != null) {
            redirectUrl += "?error=" + java.net.URLEncoder.encode(error, "UTF-8");
        }
        response.sendRedirect(redirectUrl);
    }
}