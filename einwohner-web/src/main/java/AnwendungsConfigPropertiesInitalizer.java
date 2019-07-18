import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Application Initializer to load properties from Configuration server
 */
public class AnwendungsConfigPropertiesInitalizer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigServerPropertySource configPropertySource = new ConfigServerPropertySource("Config Server");
        applicationContext.getEnvironment().getPropertySources().addFirst(configPropertySource);
    }

    private static class ConfigServerPropertySource extends PropertySource {

        private Map<String,Object> applicationProperties;

        public ConfigServerPropertySource(String name) {
            super(name);
            RestTemplate restTemplate = new RestTemplate();
            ConfigServerProperties configServerProperties = restTemplate.getForObject("http://localhost:8888/einwohner/dev", ConfigServerProperties.class);
            applicationProperties = configServerProperties.propertySources.get(0).source;
        }

        @Override
        public Object getProperty(String name) {
            return applicationProperties.get(name);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ConfigServerProperties {
        @JsonProperty
        private String name;
        @JsonProperty
        private String label;
        @JsonProperty
        private String version;
        @JsonProperty
        private String state;
        @JsonProperty
        private List<ConfigPropertySource> propertySources;

        @Override
        public String toString() {
            return "ConfigServerProperties{" +
                    "name='" + name + '\'' +
                    ", label='" + label + '\'' +
                    ", version='" + version + '\'' +
                    ", state='" + state + '\'' +
                    ", configPropertySources=" + propertySources +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ConfigPropertySource {
        @JsonProperty
        private String name;

        private Map<String, Object> source;

        public Map<String, Object> getSource() {
            return source;
        }

        @Override
        public String toString() {
            return "ConfigPropertySource{" +
                    "name='" + name + '\'' +
                    ", source=" + getSource() +
                    '}';
        }
    }
}
