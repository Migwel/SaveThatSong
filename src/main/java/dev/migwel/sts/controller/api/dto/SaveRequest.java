package dev.migwel.sts.controller.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class SaveRequest<T extends FromRequest, U extends ToRequest> {
    @NotNull @Valid private final T from;
    @NotNull @Valid private final U to;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SaveRequest(@JsonProperty("from") T from, @JsonProperty("to") U to) {
        this.from = from;
        this.to = to;
    }

    public T getFrom() {
        return from;
    }

    public U getTo() {
        return to;
    }
}
