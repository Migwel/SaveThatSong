package dev.migwel.sts.database;

import dev.migwel.sts.domain.model.ToDatabaseRequest;
import dev.migwel.sts.domain.model.Song;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ToDatabaseServiceManualTest {

    private final SongRepository songRepository;
    private final ToDatabaseService saveService;

    @Autowired
    ToDatabaseServiceManualTest(SongRepository songRepository, ToDatabaseService saveService) {
        this.songRepository = songRepository;
        this.saveService = saveService;
    }

    @Test
    void save_success() {
        Song song = new Song("artist", "title", "artist - title");
        saveService.save(song, new ToDatabaseRequest());
        Optional<dev.migwel.sts.database.entities.Song> persistedSong =
                songRepository.findByArtistAndTitle("artist", "title");
        Assertions.assertTrue(persistedSong.isPresent());
    }
}
