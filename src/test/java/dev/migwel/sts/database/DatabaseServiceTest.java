package dev.migwel.sts.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.migwel.sts.database.entities.Converter;
import dev.migwel.sts.database.entities.Song;
import dev.migwel.sts.domain.model.PersistedSong;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DatabaseServiceTest {
    @Mock SongRepository songRepository;
    @Spy Converter converter;
    @InjectMocks DatabaseService databaseService;

    @Test
    void getSongs_noSongFound() {
        when(songRepository.findByUsername(any(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        List<PersistedSong> songs = databaseService.getSongs("username", 10, 0);
        assertTrue(songs.isEmpty());
    }

    @Test
    void getSongs_oneSongFound() {
        when(songRepository.findByUsername(any(), anyInt(), anyInt()))
                .thenReturn(
                        Collections.singletonList(
                                new Song(
                                        1L,
                                        "username",
                                        "artist",
                                        "title",
                                        "artist - title",
                                        new Date())));
        List<PersistedSong> songs = databaseService.getSongs("username", 10, 0);
        assertFalse(songs.isEmpty());
        assertEquals(1, songs.size());
    }

    @Test
    void getSongs_multipleSongsFound() {
        when(songRepository.findByUsername(any(), anyInt(), anyInt()))
                .thenReturn(
                        List.of(
                                new Song(
                                        1L,
                                        "username",
                                        "artist",
                                        "title",
                                        "artist - title",
                                        new Date()),
                                new Song(
                                        2L,
                                        "username",
                                        "artist",
                                        "otherTitle",
                                        "artist - otherTitle",
                                        new Date())));
        List<PersistedSong> songs = databaseService.getSongs("username", 10, 0);
        assertFalse(songs.isEmpty());
        assertEquals(2, songs.size());
    }

    @Test
    void getSongs_songsAreOrdered() {
        Song songDay1 =
                new Song(
                        1L,
                        "username",
                        "artist",
                        "title",
                        "artist - title",
                        Date.from(Instant.parse("2023-01-01T10:00:00.00Z")));
        Song songDay2 =
                new Song(
                        1L,
                        "username",
                        "artist",
                        "otherTitle",
                        "artist - otherTitle",
                        Date.from(Instant.parse("2023-01-02T10:00:00.00Z")));
        Song songDay3 =
                new Song(
                        1L,
                        "username",
                        "otherArtist",
                        "yetAnotherTitle",
                        "otherArtist - yetAnotherTitle",
                        Date.from(Instant.parse("2023-01-03T10:00:00.00Z")));
        when(songRepository.findByUsername(any(), anyInt(), anyInt()))
                .thenReturn(List.of(songDay2, songDay3, songDay1));
        List<PersistedSong> songs = databaseService.getSongs("username", 10, 0);
        assertFalse(songs.isEmpty());
        assertEquals(3, songs.size());
        Date previousSongDate = null;
        for (PersistedSong song : songs) {
            if (previousSongDate == null) {
                previousSongDate = song.creationDate();
                continue;
            }
            assertTrue(song.creationDate().after(previousSongDate));
            previousSongDate = song.creationDate();
        }
    }

    @Test
    void getSongs_negativePageNumber() {
        assertThrows(
                IllegalArgumentException.class, () -> databaseService.getSongs("username", 10, -1));
    }

    @Test
    void getSongs_negativeLimitNumber() {
        assertThrows(
                IllegalArgumentException.class, () -> databaseService.getSongs("username", -1, 10));
    }

    @Test
    void getSongs_tooHighLimit() {
        databaseService.getSongs("username", 100, 0);
        verify(songRepository).findByUsername(any(), eq(50), anyInt());
    }
}
