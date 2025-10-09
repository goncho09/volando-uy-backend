package uy.volando.servlets;

import com.app.clases.Factory;
import com.app.clases.ISistema;
import com.app.clases.Paquete;
import com.app.datatypes.DtPaquete;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "CrearPaqueteServlet", urlPatterns = {"/paquete/crear","/package/new", "/package/create", "/paquete/new", "/paquete/create"})
public class CrearPaqueteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/paquete/crear.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws jakarta.servlet.ServletException, java.io.IOException {

        try{
        ISistema sistema = Factory.getSistema();

        String nombre = request.getParameter("nombre");

        if(sistema.existePaquete(nombre)){
            request.setAttribute("error", "Ya existe un paquete con ese nombre");
            request.getRequestDispatcher("/WEB-INF/jsp/paquete/crear.jsp").forward(request, response);
        }

        String descripcion = request.getParameter("descripcion");
        Integer validezDias = Integer.parseInt(request.getParameter("validez"));
        Integer descuento = Integer.parseInt(request.getParameter("descuento"));
        Float precio = Float.parseFloat(request.getParameter("precio"));

        if(!descripcion.isEmpty() && validezDias > 0 && descuento >= 0 && descuento <= 100 && precio > 0){

            sistema.altaPaquete(new DtPaquete(nombre, descripcion, validezDias, descuento, precio));
            request.setAttribute("success", "Paquete creado con éxito");
            request.getRequestDispatcher("/WEB-INF/jsp/paquete/crear.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Datos inválidos. Por favor, verifique e intente nuevamente.");
            request.getRequestDispatcher("/WEB-INF/jsp/paquete/crear.jsp").forward(request, response);
        }


        } catch (Exception ex){
            request.setAttribute("error", ex.getMessage());
        }

    }

}
