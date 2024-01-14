package dev.migwel.sts.spotify.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagingTrackObject {
    private final String href;
    private final List<Track> items;

    @JsonCreator
    public PagingTrackObject(String href, List<Track> items) {
        this.href = href;
        this.items = Collections.unmodifiableList(items);
    }

    public String getHref() {
        return href;
    }

    public List<Track> getItems() {
        return items;
    }
}
