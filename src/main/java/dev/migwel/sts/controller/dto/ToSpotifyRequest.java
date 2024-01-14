package dev.migwel.sts.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ToSpotifyRequest extends ToRequest {

    @JsonCreator
    public ToSpotifyRequest(@JsonProperty("type") String type) {
        super(type);
    }

    public ToSpotifyRequest() {
        super("SPOTIFY");
    }
}
