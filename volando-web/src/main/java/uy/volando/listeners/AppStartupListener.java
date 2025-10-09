package uy.volando.listeners;

import com.app.clases.Categoria;
import com.app.clases.Factory;
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
            List<Categoria> categorias = sistema.getCategorias();

            sce.getServletContext().setAttribute("categorias", categorias);

            System.out.println(">>> Categorías cargadas correctamente al iniciar la app (" + categorias.size() + ")");
        } catch (Exception e) {
            System.err.println("Error al cargar categorías en el listener:");
            e.printStackTrace();
        }
    }
}
