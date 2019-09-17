package com.sharma.config.server.client;

import com.sharma.data.resource.configserver.PayloadToDecrypt;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class DecryptTextConfigServerRequestSender extends AbstractConfigServerRequestSender {
    public DecryptTextConfigServerRequestSender(Environment springApplicationEnvironment) {
        super(springApplicationEnvironment);
    }

    public String postDecryptText(PayloadToDecrypt payloadToDecrypt){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PayloadToDecrypt> httpEntity = new HttpEntity<>(payloadToDecrypt, headers);
        ResponseEntity<String> decryptedText = restTemplate.postForEntity(getUrl(),httpEntity, String.class);
        return decryptedText.getBody();
    }

    private String getUrl(){
        return String.format("%s/%s", configServerBaseUrl, "decrypt-with-vault");
    }
}