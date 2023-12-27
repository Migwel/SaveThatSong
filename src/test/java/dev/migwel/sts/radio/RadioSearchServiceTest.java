package dev.migwel.sts.radio;

import dev.migwel.icyreader.IcyReader;
import dev.migwel.icyreader.SongInfo;
import dev.migwel.sts.model.RadioSearchRequest;
import dev.migwel.sts.model.Song;
import dev.migwel.sts.model.SonosSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RadioSearchServiceTest {

    private final IcyReader icyReader = mock(IcyReader.class);
    private final IcyReaderProvider icyReaderProvider = mock(IcyReaderProvider.class);
    private final RadioSearchService radioSearchService = new RadioSearchService(icyReaderProvider);

    @BeforeEach
    void before() {
        doReturn(icyReader).when(icyReaderProvider).getIcyReader(any());
    }

    @Test
    void isRelevant_radioSearchRequest() {
        assertTrue(radioSearchService.isRelevant(RadioSearchRequest.class));
    }

    @Test
    void isRelevant_otherSearchRequest() {
        assertFalse(radioSearchService.isRelevant(SonosSearchRequest.class));
    }

    @Test
    void search_songCanBeFound() {
        String artist = "Great artist";
        String title = "Amazing song";
        doReturn(new SongInfo("raw", artist, title)).when(icyReader).currentlyPlaying();
        Optional<Song> optionalSong = radioSearchService.search(new RadioSearchRequest("validUrl"));
        assertTrue(optionalSong.isPresent());
        Song song = optionalSong.get();
        assertEquals(artist, song.artist());
        assertEquals(title, song.title());
    }

    @Test
    void search_songCannotBeFound() {
        doReturn(null).when(icyReader).currentlyPlaying();
        Optional<Song> optionalSong = radioSearchService.search(new RadioSearchRequest("validUrl"));
        assertTrue(optionalSong.isEmpty());
    }

    @Test
    void search_songWithOnlyRawData() {
        doReturn(new SongInfo("rawData", null, null)).when(icyReader).currentlyPlaying();
        Optional<Song> optionalSong = radioSearchService.search(new RadioSearchRequest("validUrl"));
        assertTrue(optionalSong.isEmpty());
    }
}
