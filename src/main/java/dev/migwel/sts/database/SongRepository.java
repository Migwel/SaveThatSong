package dev.migwel.sts.database;

import dev.migwel.sts.database.entities.Song;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface SongRepository extends Repository<Song, Long> {
    Song save(Song song);

    Optional<Song> findByArtistAndTitle(String artist, String title);
}
