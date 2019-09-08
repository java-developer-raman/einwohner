import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharma.core.collaborator.ApplicationManifestReader;
import com.sharma.core.collaborator.SslBasedRestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Manifest;

/**
 * Application Initializer to load properties from Configuration server
 */
public class AnwendungsConfigPropertiesInitalizer implements ApplicationContextInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AnwendungsConfigPropertiesInitalizer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Properties applicationDefaultProperties = loadApplicationDefaultProperties(applicationContext);
        ConfigServerPropertySource configPropertySource = new ConfigServerPropertySource("Config Server", applicationDefaultProperties);
        applicationContext.getEnvironment().getPropertySources().addFirst(configPropertySource);
    }

    private Properties loadApplicationDefaultProperties(ConfigurableApplicationContext applicationContext) {
        String applicationConfigDir = (String) applicationContext.getEnvironment().getSystemProperties().get("app.conf.dir");
        if (applicationConfigDir == null) {
            logger.warn("Can not find environment property {app.conf.dir}. Please check if it was provided at application startup.");
        }
        try {
            return PropertiesLoaderUtils.loadProperties(new FileSystemResource(applicationConfigDir + "/einwohner-application-basic.properties"));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Either file not found or Could not load properties from file %s", applicationConfigDir + "/einwohner-application-basic.properties"), e);
        }
    }

    private static class ConfigServerPropertySource extends PropertySource {

        private Map<String, Object> propertiesLoadedFromConfigServer;

        public ConfigServerPropertySource(String name, Properties applicationDefaultProperties) {
            super(name);
            RestTemplate restTemplate = SslBasedRestTemplateFactory.createRestTemplate(applicationDefaultProperties);
            String configServerUrl = getUrlToFetchApplicationPropertiesFromConfigServer(applicationDefaultProperties);
            logger.info("Trying to load application properties from config server " + configServerUrl);
            ConfigServerProperties configServerProperties = restTemplate.getForObject(configServerUrl, ConfigServerProperties.class);
            propertiesLoadedFromConfigServer = configServerProperties.propertySources.get(0).source;
            logger.info("Application properties loaded from config server");
        }

        @Override
        public Object getProperty(String name) {
            return propertiesLoadedFromConfigServer.get(name);
        }

        private String getUrlToFetchApplicationPropertiesFromConfigServer(Properties applicationDefaultProperties) {
            String configServerUrl = applicationDefaultProperties.getProperty("config.server.url");
            Manifest manifest = ApplicationManifestReader.getManifest();
            String resourcePath = String.format("%s-%s/%s", applicationDefaultProperties.getProperty("spring.application.name"),
                    manifest.getMainAttributes().getValue("Implementation-Version"), applicationDefaultProperties.getProperty("env"));
            return String.format("%s/%s", configServerUrl, resourcePath);
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
