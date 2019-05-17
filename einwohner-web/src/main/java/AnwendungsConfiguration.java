import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.sharma.web", "com.sharma.core"})
@PropertySource("classpath:application.default.properties")
@PropertySource(value = "file:/tmp/application.properties", ignoreResourceNotFound = true)
public class AnwendungsConfiguration {
}
