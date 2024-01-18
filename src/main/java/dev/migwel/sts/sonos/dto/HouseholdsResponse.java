package dev.migwel.sts.sonos.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class HouseholdsResponse {
    private final List<Household> households;

    @JsonCreator
    public HouseholdsResponse(List<Household> households) {
        this.households = List.copyOf(households);
    }

    public List<Household> getHouseholds() {
        return List.copyOf(households);
    }
}
