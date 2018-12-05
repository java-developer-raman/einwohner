import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AnwendungsInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                AnwendungsConfiguration.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{
                "/rest/*"
        };
    }
}