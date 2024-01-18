package dev.migwel.sts.sonos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties
@PropertySource("classpath:sonos/sonos.properties")
public class SonosConfiguration {

    private String controlBaseUrl;

    public String getControlBaseUrl() {
        return controlBaseUrl;
    }

    public void setControlBaseUrl(String controlBaseUrl) {
        this.controlBaseUrl = controlBaseUrl;
    }
}
