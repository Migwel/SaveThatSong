package dev.migwel.sts.sonos.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupsResponse {
    private final List<Group> groups;

    @JsonCreator
    public GroupsResponse(List<Group> groups) {
        this.groups = List.copyOf(groups);
    }

    public List<Group> getGroups() {
        return List.copyOf(groups);
    }
}
