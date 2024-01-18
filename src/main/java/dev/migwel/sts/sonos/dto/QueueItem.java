package dev.migwel.sts.sonos.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueItem {
    private final Track track;

    @JsonCreator
    public QueueItem(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }
}
