package dev.migwel.sts.controller.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ToDatabaseRequest.class, name = "DATABASE"),
    @JsonSubTypes.Type(value = ToSpotifyRequest.class, name = "SPOTIFY"),
})
public abstract sealed class ToRequest permits ToDatabaseRequest, ToSpotifyRequest {
    private final String type;

    protected ToRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
