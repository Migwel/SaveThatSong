package dev.migwel.sts.controller.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ToDatabaseRequest extends ToRequest {

    @JsonCreator
    public ToDatabaseRequest(@JsonProperty("type") String type) {
        super(type);
    }

    public ToDatabaseRequest() {
        super("DATABASE");
    }
}
