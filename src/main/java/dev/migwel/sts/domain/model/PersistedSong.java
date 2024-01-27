package dev.migwel.sts.domain.model;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Date;

@ParametersAreNonnullByDefault
public record PersistedSong(Song song, String username, Date creationDate) {
    public PersistedSong(Song song, String username, Date creationDate) {
        this.song = song;
        this.username = username;
        this.creationDate = new Date(creationDate.getTime());
    }

    public Date creationDate() {
        return new Date(creationDate.getTime());
    }
}
