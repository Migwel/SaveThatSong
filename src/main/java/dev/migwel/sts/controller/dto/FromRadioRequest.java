package dev.migwel.sts.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class FromRadioRequest extends FromRequest {
    @NotBlank private final String url;

    @JsonCreator
    public FromRadioRequest(@JsonProperty("type") String type, @JsonProperty("url") String url) {
        super(type);
        this.url = url;
    }

    public FromRadioRequest(String url) {
        super("RADIO");
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
