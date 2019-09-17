package com.sharma.config.server.client;

import com.sharma.data.resource.configserver.ConfigServerProperties;
import com.sharma.shared.collaborator.ApplicationManifestReader;
import org.springframework.core.env.Environment;

import java.util.jar.Manifest;

public class ConfigServerPropertiesLoader extends AbstractConfigServerRequestSender {

    private Environment springApplicationEnvironment;

    public ConfigServerPropertiesLoader(Environment springApplicationEnvironment) {
        super(springApplicationEnvironment);
        this.springApplicationEnvironment = springApplicationEnvironment;
    }

    private String createUrlToFetchApplicationPropertiesFromConfigServer() {
        Manifest manifest = ApplicationManifestReader.getManifest();
        String resourcePath = String.format("%s-%s/%s", springApplicationEnvironment.getProperty("spring.application.name"),
                manifest.getMainAttributes().getValue("Implementation-Version"), springApplicationEnvironment.getProperty("env"));
        return String.format("%s/%s", configServerBaseUrl, resourcePath);
    }

    public ConfigServerProperties getConfigServerProperties(){
        String configServerUrl = createUrlToFetchApplicationPropertiesFromConfigServer();
        return restTemplate.getForObject(configServerUrl, ConfigServerProperties.class);
    }
}