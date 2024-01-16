package dev.migwel.sts.domain.model;

import javax.annotation.CheckForNull;

public class SaveResult {
    private final Song song;
    private final ToResult toResult;

    public SaveResult(@CheckForNull Song song, @CheckForNull ToResult toResult) {
        this.song = song;
        this.toResult = toResult;
    }

    public static SaveResult noSongFound() {
        return new SaveResult(null, null);
    }

    public Song getSong() {
        return song;
    }

    public ToResult getToResult() {
        return toResult;
    }
}
