package dev.migwel.sts.sonos.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaybackMetadataResponse {
    private final QueueItem currentItem;
    private final String streamInfo;

    @JsonCreator
    public PlaybackMetadataResponse(QueueItem currentItem, String streamInfo) {
        this.currentItem = currentItem;
        this.streamInfo = streamInfo;
    }

    public QueueItem getCurrentItem() {
        return currentItem;
    }

    public String getStreamInfo() {
        return streamInfo;
    }
}
