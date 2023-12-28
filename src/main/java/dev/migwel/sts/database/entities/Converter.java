package dev.migwel.sts.database.entities;

import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@Component
@ParametersAreNonnullByDefault
public class Converter {
    public Song convert(dev.migwel.sts.model.Song model) {
        return new Song(model.artist(), model.title(), model.rawData());
    }

    public dev.migwel.sts.model.Song convert(Song entity) {
        return new dev.migwel.sts.model.Song(
                entity.getArtist(), entity.getTitle(), entity.getRawData());
    }
}
