package dev.migwel.sts.sonos.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    private final String name;

    @JsonCreator
    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
