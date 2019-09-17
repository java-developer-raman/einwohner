import com.sharma.config.server.client.ConfigServerPropertiesLoader;
import com.sharma.data.resource.configserver.ConfigServerProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Application Initializer to load properties from Configuration server
 */
public class AnwendungsConfigPropertiesInitalizer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // We need to load application's basic properties from filesystem manually, Because till this point spring environment has not
        // loaded all the properties in it's environment
        Properties applicationPropertiesFromDisk = loadApplicationPropertiesFromDisk(applicationContext);
        setTrustSoreAsSystemProperty(applicationPropertiesFromDisk);
        //Loading properties from config server and adding them into environment
        applicationContext.getEnvironment().getPropertySources().addFirst(new ApplicationPropertiesSource("Application properties from Disk", applicationPropertiesFromDisk));
        applicationContext.getEnvironment().getPropertySources().addLast(new ConfigServerPropertySource("Application properties from Config Server", applicationContext.getEnvironment()));
    }

    private void setTrustSoreAsSystemProperty(Properties applicationProperties) {
        System.setProperty("javax.net.ssl.trustStore", applicationProperties.get("server.ssl.trust-store").toString());
        System.setProperty("javax.net.ssl.trustStorePassword", applicationProperties.get("server.ssl.trust-store-password").toString());
    }

    private Properties loadApplicationPropertiesFromDisk(ConfigurableApplicationContext applicationContext) {
        String applicationPropertiesFilePath = getFullPathToApplicationProperties(applicationContext);
        try {
            return PropertiesLoaderUtils.loadProperties(new FileSystemResource(applicationPropertiesFilePath));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Either file not found or Could not load properties from file %s", applicationPropertiesFilePath), e);
        }
    }

    private String getFullPathToApplicationProperties(ConfigurableApplicationContext applicationContext) {
        String configLocation = (String) applicationContext.getEnvironment().getSystemProperties().get("spring.config.location");
        String configFileName = (String) applicationContext.getEnvironment().getSystemProperties().get("spring.config.name");
        if (StringUtils.isEmpty(configLocation) || StringUtils.isEmpty(configFileName)) {
            throw new RuntimeException("Please provide system properties spring.config.location and spring.config.name");
        }
        return String.format("%s%s", configLocation, configFileName);
    }

    private static class ApplicationPropertiesSource extends PropertySource {

        private Properties applicationPropertiesFromDisk;

        public ApplicationPropertiesSource(String name, Properties applicationPropertiesFromDisk) {
            super(name);
            this.applicationPropertiesFromDisk = applicationPropertiesFromDisk;
        }

        @Override
        public Object getProperty(String name) {
            return applicationPropertiesFromDisk.getProperty(name);
        }
    }

    private static class ConfigServerPropertySource extends PropertySource {

        private ConfigServerProperties applicationPropertiesFromConfigServer;

        public ConfigServerPropertySource(String name, ConfigurableEnvironment springApplicationEnvironment) {
            super(name);
            ConfigServerPropertiesLoader configServerPropertiesLoader = new ConfigServerPropertiesLoader(springApplicationEnvironment);
            applicationPropertiesFromConfigServer = configServerPropertiesLoader.getConfigServerProperties();
        }

        @Override
        public Object getProperty(String name) {
            return applicationPropertiesFromConfigServer.getProperty(name);
        }
    }

}
