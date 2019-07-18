import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.sharma.web", "com.sharma.core"})
@PropertySources({
        @PropertySource(value = "file:${app.conf.dir}/application.properties", ignoreResourceNotFound = true),
        @PropertySource("classpath:application.default.properties")
})
public class AnwendungsConfiguration {
}
