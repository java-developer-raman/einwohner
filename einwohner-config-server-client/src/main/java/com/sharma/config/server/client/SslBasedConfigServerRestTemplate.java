package com.sharma.config.server.client;

import com.sharma.shared.collaborator.LoggingRequestInterceptor;
import com.sharma.shared.collaborator.TrustBasedSSLConnectionSocketFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.env.Environment;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class SslBasedConfigServerRestTemplate {
    private static RestTemplate restTemplate;

    public static RestTemplate createOrGetCachedRestTemplate(Environment springApplicationEnvironment) {
        if (restTemplate != null) {
            return restTemplate;
        }
        restTemplate = new RestTemplate();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(springApplicationEnvironment.getProperty("config.server.user"), springApplicationEnvironment.getProperty("config.server.password")));

        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(TrustBasedSSLConnectionSocketFactory.createFactory())
                .setMaxConnTotal(Integer.valueOf(5))
                .setMaxConnPerRoute(Integer.valueOf(5))
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        httpComponentsClientHttpRequestFactory.setReadTimeout(Integer.valueOf(10000));
        httpComponentsClientHttpRequestFactory.setConnectTimeout(Integer.valueOf(10000));
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(httpComponentsClientHttpRequestFactory));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
