package dev.migwel.sts.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveRequest<T extends FromRequest, U extends ToRequest> {
    private final T from;
    private final U to;

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
