package uy.volando.listeners;

import com.app.clases.Factory;
import com.app.datatypes.DtAerolinea;
import com.app.datatypes.DtCategoria;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.List;
import com.app.clases.ISistema;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ISistema sistema = Factory.getSistema();
            //sistema.cargarDatos();

            List <DtAerolinea> aerolineas = sistema.listarAerolineas();
            aerolineas.removeIf(aerolinea -> aerolinea.listarRutasDeVuelo().isEmpty());

//            List < DtPaquete> paquetes = sistema.listarPaquetes();
//            List < DtRuta> rutas = sistema.listarRutasDeVuelo();

            sce.getServletContext().setAttribute("categorias", sistema.listarCategorias());
            sce.getServletContext().setAttribute("aerolineasConRuta",aerolineas);

        } catch (Exception e) {
            System.err.println("Error al cargar categorías en el listener:");
        }
    }
}
