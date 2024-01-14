package dev.migwel.sts.spotify.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    private final PagingTrackObject tracks;
    private final Long limit;
    private final String next;
    private final Long offset;
    private final String previous;
    private final Long total;

    @JsonCreator
    public SearchResponse(
            PagingTrackObject tracks,
            Long limit,
            String next,
            Long offset,
            String previous,
            Long total) {
        this.tracks = tracks;
        this.limit = limit;
        this.next = next;
        this.offset = offset;
        this.previous = previous;
        this.total = total;
    }

    public PagingTrackObject getTracks() {
        return tracks;
    }

    public Long getLimit() {
        return limit;
    }

    public String getNext() {
        return next;
    }

    public Long getOffset() {
        return offset;
    }

    public String getPrevious() {
        return previous;
    }

    public Long getTotal() {
        return total;
    }
}
