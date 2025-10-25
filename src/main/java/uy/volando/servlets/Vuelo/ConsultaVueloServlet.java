package uy.volando.servlets.Vuelo;

import com.app.clases.Factory;
import com.app.clases.Sistema;
import com.app.datatypes.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ConsultaVueloServlet", urlPatterns = {"/vuelo/consulta"})
public class ConsultaVueloServlet extends HttpServlet {
    Sistema sistema = (Sistema) Factory.getSistema();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        try {
            String idVuelo = request.getParameter("nombre");

            DtVuelo vuelo = sistema.getVuelo(idVuelo);
            DtRuta ruta = vuelo.getRutaDeVuelo();

            String basePath = request.getServletContext().getRealPath("/pictures/vuelos");
            String contextPath = request.getContextPath();

            String urlImage = vuelo.getUrlImage();
            File vueloImg = null;

            if (urlImage != null && !urlImage.isEmpty()) {
                vueloImg = new File(basePath, urlImage);
            }

            if (urlImage == null || urlImage.isEmpty() || !vueloImg.exists()) {
                vuelo.setUrlImage(contextPath + "/assets/vueloDefault.jpg");
            } else {
                vuelo.setUrlImage(contextPath + "/pictures/vuelos/" + urlImage);
            }

            request.setAttribute("ruta", ruta);
            request.setAttribute("vuelo", vuelo);

            HttpSession session = request.getSession(false);

            if(session.getAttribute("usuarioNickname") != null && session.getAttribute("usuarioTipo") != null) {
                String tipo = session.getAttribute("usuarioTipo").toString();
                if(tipo.equals("aerolinea")) {
                    DtAerolinea aerolineaObj = sistema.getAerolinea(session.getAttribute("usuarioNickname").toString());
                    for (DtRuta r : aerolineaObj.listarRutasDeVuelo()) {
                        if (r.getNombre().equals(ruta.getNombre())) {
                            request.setAttribute("esDeLaAerolinea", true);
                            break;
                        }
                    }
                }else{
                    DtCliente cliente = sistema.getCliente(session.getAttribute("usuarioNickname").toString());
                    List<DtReserva> reservas = sistema.listarReservasClienteVuelo(cliente,vuelo);
                    if(!reservas.isEmpty()) {
                        request.setAttribute("tieneReserva", true);
                    }
                }

            }
            request.getRequestDispatcher("/WEB-INF/jsp/vuelo/consulta.jsp").forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("error", "Error al cargar la consulta de vuelos");
            request.getRequestDispatcher("/WEB-INF/jsp/vuelo/buscar.jsp").forward(request, response);
            System.err.println("Error al obtener parametros: " + ex.getMessage());
        }
    }
}