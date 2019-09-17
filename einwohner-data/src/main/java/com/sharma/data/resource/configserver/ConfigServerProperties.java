package com.sharma.data.resource.configserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigServerProperties {
    @JsonProperty
    private String name;
    @JsonProperty
    private String label;
    @JsonProperty
    private String version;
    @JsonProperty
    private String state;
    @JsonProperty
    private List<ConfigPropertySource> propertySources;

    private Map<String, Object> properties;

    public String getProperty(String propertyName){
        if(properties == null){
            properties = propertySources.get(0).getSource();
        }
        return (String) properties.get(propertyName);
    }

    @Override
    public String toString() {
        return "ConfigServerProperties{" +
                "name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", version='" + version + '\'' +
                ", state='" + state + '\'' +
                ", configPropertySources=" + propertySources +
                '}';
    }
}

