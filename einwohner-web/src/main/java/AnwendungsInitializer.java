import com.sharma.db.LiquibaseConfig;
import com.sharma.orm.OrmConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AnwendungsInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AnwendungsConfiguration.class, OrmConfiguration.class, LiquibaseConfig.class);
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);
        container.addListener(contextLoaderListener);
        container.setInitParameter("contextInitializerClasses", "AnwendungsConfigPropertiesInitalizer");
        DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", dispatcherServlet);
        dispatcher.addMapping("/rest/*");
    }
}