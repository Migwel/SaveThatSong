package dev.migwel.sts.spotify.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    private final String id;

    @JsonCreator
    public Track(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
