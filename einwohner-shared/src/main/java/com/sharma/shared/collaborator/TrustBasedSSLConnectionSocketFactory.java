package com.sharma.shared.collaborator;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.springframework.core.io.FileSystemResource;

import java.security.KeyStore;

public class TrustBasedSSLConnectionSocketFactory {

    private static SSLConnectionSocketFactory cachedSSLConnectionSocketFactory = null;

    public static SSLConnectionSocketFactory createFactory(){
        if(cachedSSLConnectionSocketFactory != null){
            return cachedSSLConnectionSocketFactory;
        }
        FileSystemResource fileSystemResource = new FileSystemResource(System.getProperty("javax.net.ssl.trustStore"));
        try {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            char[] keypass = System.getProperty("javax.net.ssl.trustStorePassword").toCharArray();

            keyStore.load(fileSystemResource.getInputStream(), keypass);

            cachedSSLConnectionSocketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(keyStore, keypass).build(),
                    NoopHostnameVerifier.INSTANCE);
            return cachedSSLConnectionSocketFactory;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create a resttemplate required fro ssl communication.", e);
        }
   }
}
