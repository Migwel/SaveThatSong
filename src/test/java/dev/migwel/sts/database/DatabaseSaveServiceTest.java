package dev.migwel.sts.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.migwel.sts.database.entities.Converter;
import dev.migwel.sts.exception.SaveToException;
import dev.migwel.sts.model.PersistSaveRequest;
import dev.migwel.sts.model.Song;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

@ExtendWith(MockitoExtension.class)
class DatabaseSaveServiceTest {

    @Mock private SongRepository songRepository;
    @Spy private Converter converter;
    @InjectMocks private DatabaseSaveService saveService;

    @Test
    void save_success() {
        Song song = new Song("artist", "title", "artist - title");
        dev.migwel.sts.database.entities.Song entitySong =
                new dev.migwel.sts.database.entities.Song(1L, "artist", "title", "artist - title");
        doReturn(entitySong).when(songRepository).save(any());
        saveService.save(song, new PersistSaveRequest());
    }

    @Test
    void save_persistFailure() {
        doThrow(new InvalidDataAccessResourceUsageException("Could not write song"))
                .when(songRepository)
                .save(any());
        Song song = new Song("artist", "title", "artist - title");
        assertThrows(SaveToException.class, () -> saveService.save(song, new PersistSaveRequest()));
    }
}
