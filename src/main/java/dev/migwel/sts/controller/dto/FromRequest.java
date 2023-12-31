package dev.migwel.sts.controller.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = FromRadioRequest.class, name = "RADIO"),
    @JsonSubTypes.Type(value = FromSonosRequest.class, name = "SONOS")
})
public abstract sealed class FromRequest permits FromRadioRequest, FromSonosRequest {

    @NotBlank private final String type;

    public FromRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
