package com.sharma.data.resource.configserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayloadToDecrypt {
    @JsonProperty
    private String textToDecrypt;

    @JsonProperty
    private String applicationName;
}
