package com.sharma.core.collaborator;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyStore;
import java.util.Properties;

public class SslBasedRestTemplateFactory {

    public static RestTemplate createRestTemplate(Properties applicationDefaultProperties){
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource fileSystemResource = new FileSystemResource(applicationDefaultProperties.getProperty("server.ssl.key-store"));
        try {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            char[] keypass = applicationDefaultProperties.getProperty("server.ssl.key-store-password").toCharArray();

            keyStore.load(fileSystemResource.getInputStream(), keypass);

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(keyStore, keypass).build(),
                    NoopHostnameVerifier.INSTANCE);

            HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
                    .setMaxConnTotal(Integer.valueOf(5))
                    .setMaxConnPerRoute(Integer.valueOf(5))
                    .build();

            HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            httpComponentsClientHttpRequestFactory.setReadTimeout(Integer.valueOf(10000));
            httpComponentsClientHttpRequestFactory.setConnectTimeout(Integer.valueOf(10000));
            restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory);
            return restTemplate;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create a resttemplate required fro ssl communication.", e);
        }
   }
}
