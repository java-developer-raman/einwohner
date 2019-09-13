import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.sharma.web", "com.sharma.core", "org.springframework.vault.config"})
@PropertySources({
        @PropertySource(value = "file:${spring.config.location}/${spring.config.name}", ignoreResourceNotFound = true),
        @PropertySource("classpath:application.default.properties")
})
public class AnwendungsConfiguration {
}
