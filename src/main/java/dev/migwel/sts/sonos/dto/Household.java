package dev.migwel.sts.sonos.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Household {
    private final String id;

    @JsonCreator
    public Household(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
