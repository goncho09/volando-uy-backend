package uy.volando.servlets.Sesion;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtCliente;
import com.app.datatypes.DtUsuario;
import com.app.enums.TipoDocumento;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

@WebServlet (name = "RegistrarUsuarioFinal", urlPatterns = {"/register/final","/signup/final"})
public class RegistrarUsuarioFinalServlet extends HttpServlet {

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/signup/registrarUsuarioFinal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try {

                if(request.getSession(false) != null){
                    String tipoUsuario = request.getSession().getAttribute("tipoUsuario").toString();
                    DtUsuario dtUsuario = (DtUsuario) request.getSession().getAttribute("datosUsuario");
                    ISistema sistema = Factory.getSistema();

                    // TODO: Manejar la carga de imagenes
                    dtUsuario.setUrlImage("pictures/users/default.png");

                    if (tipoUsuario.equals("aerolinea")) {
                        String descripcion = request.getParameter("descripcion");
                        String urlSitioWeb = request.getParameter("pagina-web");

                        if(descripcion.isEmpty()){
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("Por favor, complete todos los campos obligatorios.");
                        }

                        if(urlSitioWeb.isEmpty()){
                            urlSitioWeb = "";
                        }
                        if(!urlSitioWeb.isEmpty() && !urlSitioWeb.startsWith("http://") && !urlSitioWeb.startsWith("https://")){
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("La URL del sitio web debe comenzar con http:// o https://");
                            return;
                        }

                        sistema.registrarAerolinea(new DtAerolinea(dtUsuario, descripcion, urlSitioWeb));
                        request.getSession().invalidate();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } else{
                        String apellido = request.getParameter("apellido");
                        String fechaNacimiento = request.getParameter("fecha-nacimiento");
                        String nacionalidad = request.getParameter("nacionalidad");
                        String numeroDocumento = request.getParameter("numero-documento");
                        String tipoDocumento = request.getParameter("tipo-documento");


                        if(apellido.isEmpty() || fechaNacimiento.isEmpty() || nacionalidad.isEmpty() || numeroDocumento.isEmpty() || tipoDocumento.isEmpty()){
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("Por favor, complete todos los campos obligatorios.");
                        }

                        LocalDate fechaNac = null;
                        try {
                            fechaNac = LocalDate.parse(fechaNacimiento);
                        } catch (Exception e) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("El formato de la fecha de nacimiento es inválido.");
                            return;
                        }
                        System.out.println("1");

                        int numDocumento;
                        try {
                            numDocumento = Integer.parseInt(numeroDocumento);
                        } catch (NumberFormatException e) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("El número de documento debe ser un valor numérico.");
                            return;
                        }
                        System.out.println("2");

                        if(fechaNac.isAfter(LocalDate.now())){
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("La fecha de nacimiento no puede ser en el futuro.");
                            return;
                        }
                        System.out.println("3");

                        System.out.println("Tipo documento: " + TipoDocumento.valueOf(tipoDocumento));
                        System.out.println("4");
                        sistema.registrarCliente(new DtCliente(dtUsuario, apellido, fechaNac, nacionalidad, TipoDocumento.valueOf(tipoDocumento), numDocumento));
                        System.out.println("5");
                        request.getSession().invalidate();
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                }else{
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("No se ha iniciado el registro correctamente.");
                }

        }  catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
        }
    }
}
