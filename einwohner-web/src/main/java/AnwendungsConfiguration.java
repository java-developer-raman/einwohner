import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.sharma.web", "com.sharma.core", "com.sharma.shared", "com.sharma.config.server.client"})
@PropertySources({
        @PropertySource("classpath:application.default.properties")
})
public class AnwendungsConfiguration {
    /**
     * Normally if you put @Value annotation in @Configuration classes, it does not resolve into values of property, although properties
     * are available in spring Environment, So to allow @Value to work properly, we need to create this bean manually.
     *
     * eigentlich properties mit @Value funktionert in Controller und Service classes, aber nicht in @Configuration classes, deswegen
     * spring brauchen diese Bean in spring applikationen kontext sodass die auch in @Configuration class funkionern kann.
     *
     * This is required only in case of @Configuration classes, it work in normal Controller classes without this bean.
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
