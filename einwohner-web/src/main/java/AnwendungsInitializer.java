import com.sharma.db.LiquibaseConfig;
import com.sharma.orm.OrmConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AnwendungsInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                AnwendungsConfiguration.class, OrmConfiguration.class, LiquibaseConfig.class
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