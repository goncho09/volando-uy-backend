package uy.volando.servlets.Perfil;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.*;
import com.app.enums.TipoDocumento;
import com.app.enums.TipoImagen;
import com.app.utils.AuxiliarFunctions;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "ModificarPerfilServlet", urlPatterns = {"/modificar-perfil"})
public class ModificarPerfilServlet extends HttpServlet {
    ISistema sistema = Factory.getSistema();

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

        String success = null;
        String error = null;

        try {
            // Obtén DtUsuario actual de sesión
            DtUsuario usuarioActual = (DtUsuario) session.getAttribute("usuario");
            if (usuarioActual == null) {
               usuarioActual = sistema.getUsuario(nickname);
            }

            String fotoPerfil = usuarioActual.getUrlImage();

            Part filePart = request.getPart("imagen");

            if (filePart != null || filePart.getSize() > 0) {
                // Crear archivo temporal
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                File tempFile = File.createTempFile("upload-", "-" + fileName);

                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                AuxiliarFunctions.borrarImagen(usuarioActual.getUrlImage(), TipoImagen.USUARIO);
                File imagenGuardada = AuxiliarFunctions.guardarImagen(tempFile, TipoImagen.USUARIO);
                fotoPerfil = imagenGuardada.getName();

                usuarioActual.setUrlImage(fotoPerfil);
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

                DtUsuario actualizarDatosUser = new DtUsuario(
                        usuarioActual.getNickname(),
                        usuarioActual.getNombre(),
                        usuarioActual.getEmail()
                );

                // Crea DtCliente actualizado (usa valores actuales si no hay nuevos, pero como es form, asume siempre)
                DtCliente clienteActual = new DtCliente(
                        actualizarDatosUser,
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
                fotoPerfil = usuarioActual.getUrlImage();
                if (fotoPerfil != null) {
                    clienteActual.setUrlImage(fotoPerfil);
                    System.out.println(">>> Imagen seteada en clienteActual: " + fotoPerfil);
                }

                // Actualiza en sistema (excluye imagen)
                sistema.modificarCliente(clienteActual);
                if(filePart != null && filePart.getSize() > 0) {
                    sistema.modificarClienteImagen(clienteActual, fotoPerfil);
                }

                // Recarga para verificar BD
                usuarioRecargado = sistema.getCliente(nickname);
                System.out.println(">>> Cliente recargado post-mod: nombre='" + usuarioRecargado.getNombre() +
                        "', imagenBD='" + usuarioRecargado.getUrlImage() + "'");  // Debug clave: ¿coincide con nueva?

                if (fotoPerfil != null && !fotoPerfil.equals(usuarioRecargado.getUrlImage())) {
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
                if (fotoPerfil != null) {
                    aerolineaActual.setUrlImage(fotoPerfil);
                    System.out.println(">>> Imagen seteada en aerolineaActual: " + fotoPerfil);
                }

                // Actualiza en sistema (incluye imagen)
                sistema.modificarAerolinea(aerolineaActual);

                // Recarga para verificar
                usuarioRecargado = sistema.getAerolinea(nickname);
                System.out.println(">>> Aerolinea recargada post-mod: nombre='" + usuarioRecargado.getNombre() +
                        "', imagenBD='" + usuarioRecargado.getUrlImage() + "'");  // Debug clave

                if (fotoPerfil != null && !fotoPerfil.equals(usuarioRecargado.getUrlImage())) {
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
            if (fotoPerfil != null) {
                session.setAttribute("usuarioImagen", fotoPerfil);
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