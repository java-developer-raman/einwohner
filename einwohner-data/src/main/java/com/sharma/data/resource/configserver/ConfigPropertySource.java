package com.sharma.data.resource.configserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigPropertySource {
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
