package dev.migwel.sts.sonos.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    private final String id;
    private final String playbackState;
    private final List<String> playerIds;

    @JsonCreator
    public Group(String id, String playbackState, List<String> playerIds) {
        this.id = id;
        this.playbackState = playbackState;
        this.playerIds = List.copyOf(playerIds);
    }

    public String getId() {
        return id;
    }

    public String getPlaybackState() {
        return playbackState;
    }

    public List<String> getPlayerIds() {
        return List.copyOf(playerIds);
    }
}
