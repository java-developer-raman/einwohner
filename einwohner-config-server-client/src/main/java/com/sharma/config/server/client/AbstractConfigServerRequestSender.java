package com.sharma.config.server.client;

import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractConfigServerRequestSender {

    protected RestTemplate restTemplate;
    protected String configServerBaseUrl;

    public AbstractConfigServerRequestSender(Environment springApplicationEnvironment) {
        restTemplate = SslBasedConfigServerRestTemplate.createOrGetCachedRestTemplate(springApplicationEnvironment);
        this.configServerBaseUrl = springApplicationEnvironment.getProperty("config.server.url");
    }
}