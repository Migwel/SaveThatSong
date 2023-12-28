package dev.migwel.sts.database;

import dev.migwel.sts.model.Song;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class DatabaseSaveServiceManualTest {

    private final SongRepository songRepository;
    private final DatabaseSaveService saveService;

    @Autowired
    DatabaseSaveServiceManualTest(SongRepository songRepository, DatabaseSaveService saveService) {
        this.songRepository = songRepository;
        this.saveService = saveService;
    }

    @Test
    void save_success() {
        Song song = new Song("artist", "title", "artist - title");
        saveService.save(song);
        Optional<dev.migwel.sts.database.entities.Song> persistedSong =
                songRepository.findByArtistAndTitle("artist", "title");
        Assertions.assertTrue(persistedSong.isPresent());
    }
}
