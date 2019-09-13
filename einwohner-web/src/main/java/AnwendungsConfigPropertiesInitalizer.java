import com.sharma.core.collaborator.ApplicationManifestReader;
import com.sharma.core.collaborator.SslBasedRestTemplateFactory;
import com.sharma.data.resource.configserver.ConfigServerProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Manifest;

/**
 * Application Initializer to load properties from Configuration server
 */
public class AnwendungsConfigPropertiesInitalizer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // We need to load application's basic properties from filesystem manually, Because till this point spring environment has not
        // loaded all the properties in it's environment
        Properties applicationDefaultProperties = loadApplicationDefaultProperties(applicationContext);
        setTrustSoreAsSystemProperty(applicationDefaultProperties);
        //Loading properties from config server and adding them into environment
        ConfigServerPropertySource configPropertySource = new ConfigServerPropertySource("Config Server", applicationDefaultProperties);
        applicationContext.getEnvironment().getPropertySources().addFirst(configPropertySource);
    }

    private void setTrustSoreAsSystemProperty(Properties applicationProperties){
        System.setProperty("javax.net.ssl.trustStore", applicationProperties.get("server.ssl.trust-store").toString());
        System.setProperty("javax.net.ssl.trustStorePassword", applicationProperties.get("server.ssl.trust-store-password").toString());
    }

    private Properties loadApplicationDefaultProperties(ConfigurableApplicationContext applicationContext) {
        String configFile = getFullConfigPath(applicationContext);
        try {
            return PropertiesLoaderUtils.loadProperties(new FileSystemResource(configFile));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Either file not found or Could not load properties from file %s", configFile), e);
        }
    }

    private String getFullConfigPath(ConfigurableApplicationContext applicationContext){
        String configLocation = (String) applicationContext.getEnvironment().getSystemProperties().get("spring.config.location");
        String configFileName = (String) applicationContext.getEnvironment().getSystemProperties().get("spring.config.name");
        if(StringUtils.isEmpty(configLocation) || StringUtils.isEmpty(configFileName)){
            throw new RuntimeException("Please provide system properties spring.config.location and spring.config.name");
        }
        return String.format("%s%s", configLocation, configFileName);
    }

    private static class ConfigServerPropertySource extends PropertySource {

        private Map<String, Object> propertiesLoadedFromConfigServer;

        public ConfigServerPropertySource(String name, Properties applicationDefaultProperties) {
            super(name);
            RestTemplate restTemplate = SslBasedRestTemplateFactory.createRestTemplate(applicationDefaultProperties);
            String configServerUrl = getUrlToFetchApplicationPropertiesFromConfigServer(applicationDefaultProperties);
            ConfigServerProperties configServerProperties = restTemplate.getForObject(configServerUrl, ConfigServerProperties.class);
            propertiesLoadedFromConfigServer = configServerProperties.getPropertySources().get(0).getSource();
        }

        @Override
        public Object getProperty(String name) {
            return propertiesLoadedFromConfigServer.get(name);
        }

        /**
         * Config server hold application properties for each application's release e.g. einwohner-1.0-SNAPSHOT
         * Whenever we release code we create properties for that release in Vault server. So that when we do any rollback to previous
         * release it can be done automatically without any hassle.
         * @param applicationDefaultProperties
         * @return
         */
        private String getUrlToFetchApplicationPropertiesFromConfigServer(Properties applicationDefaultProperties) {
            String configServerUrl = applicationDefaultProperties.getProperty("config.server.url");
            Manifest manifest = ApplicationManifestReader.getManifest();
            String resourcePath = String.format("%s-%s/%s", applicationDefaultProperties.getProperty("spring.application.name"),
                    manifest.getMainAttributes().getValue("Implementation-Version"), applicationDefaultProperties.getProperty("env"));
            return String.format("%s/%s", configServerUrl, resourcePath);
        }
    }

}
