package dev.migwel.sts.database.entities;

import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@Component("EntitiesConverter")
@ParametersAreNonnullByDefault
public class Converter {
    public Song convert(String username, dev.migwel.sts.domain.model.Song model) {
        return new Song(username, model.artist(), model.title(), model.rawData());
    }

    public dev.migwel.sts.domain.model.Song convert(Song entity) {
        return new dev.migwel.sts.domain.model.Song(
                entity.getArtist(), entity.getTitle(), entity.getRawData());
    }

    public dev.migwel.sts.domain.model.PersistedSong convertToPersistedSong(Song entity) {
        return new dev.migwel.sts.domain.model.PersistedSong(
                convert(entity), entity.getUsername(), entity.getCreationDate());
    }
}
