package dev.migwel.sts.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.migwel.sts.database.entities.Converter;
import dev.migwel.sts.domain.exception.SaveToException;
import dev.migwel.sts.domain.model.ToDatabaseRequest;
import dev.migwel.sts.domain.model.Song;

import dev.migwel.sts.domain.model.ToResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

@ExtendWith(MockitoExtension.class)
class ToDatabaseServiceTest {

    @Mock private SongRepository songRepository;
    @Spy private Converter converter;
    @InjectMocks private ToDatabaseService saveService;

    @Test
    void save_success() {
        Song song = new Song("artist", "title", "artist - title");
        dev.migwel.sts.database.entities.Song entitySong =
                new dev.migwel.sts.database.entities.Song(
                        1L, "user", "artist", "title", "artist - title");
        doReturn(entitySong).when(songRepository).save(any());
        ToResult result = saveService.save(song, new ToDatabaseRequest("user"));
        assertTrue(result.isSuccess());
        assertNull(result.getErrorMessage());
    }

    @Test
    void save_persistFailure() {
        doThrow(new InvalidDataAccessResourceUsageException("Could not write title"))
                .when(songRepository)
                .save(any());
        Song song = new Song("artist", "title", "artist - title");
        ToResult result = saveService.save(song, new ToDatabaseRequest("user"));
        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessage());
    }
}
