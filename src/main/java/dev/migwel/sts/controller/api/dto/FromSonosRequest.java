package dev.migwel.sts.controller.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class FromSonosRequest extends FromRequest {

    private final String householdId;
    private final String groupId;

    @JsonCreator
    public FromSonosRequest(
            @JsonProperty("type") String type,
            @JsonProperty("householdId") String householdId,
            @JsonProperty("groupId") String groupId) {
        super(type);
        this.householdId = householdId;
        this.groupId = groupId;
    }

    public FromSonosRequest(String householdId, String groupId) {
        super("SONOS");
        this.householdId = householdId;
        this.groupId = groupId;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public String getGroupId() {
        return groupId;
    }
}
