package dev.migwel.sts.radio;

import dev.migwel.icyreader.IcyReader;
import dev.migwel.icyreader.SongInfo;
import dev.migwel.sts.domain.model.FromRadioRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.FromSonosRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FromRadioServiceTest {

    private final IcyReader icyReader = mock(IcyReader.class);
    private final IcyReaderProvider icyReaderProvider = mock(IcyReaderProvider.class);
    private final FromRadioService fromRadioService = new FromRadioService(icyReaderProvider);

    @BeforeEach
    void before() {
        doReturn(icyReader).when(icyReaderProvider).getIcyReader(any());
    }

    @Test
    void isRelevant_radioSearchRequest() {
        assertTrue(fromRadioService.isRelevant(FromRadioRequest.class));
    }

    @Test
    void isRelevant_otherSearchRequest() {
        assertFalse(fromRadioService.isRelevant(FromSonosRequest.class));
    }

    @Test
    void search_songCanBeFound() {
        String artist = "Great artist";
        String title = "Amazing title";
        doReturn(new SongInfo("raw", artist, title)).when(icyReader).currentlyPlaying();
        Optional<Song> optionalSong = fromRadioService.search(new FromRadioRequest("validUrl"));
        assertTrue(optionalSong.isPresent());
        Song song = optionalSong.get();
        assertEquals(artist, song.artist());
        assertEquals(title, song.title());
    }

    @Test
    void search_songCannotBeFound() {
        doReturn(null).when(icyReader).currentlyPlaying();
        Optional<Song> optionalSong = fromRadioService.search(new FromRadioRequest("validUrl"));
        assertTrue(optionalSong.isEmpty());
    }

    @Test
    void search_songWithOnlyRawData() {
        doReturn(new SongInfo("rawData", null, null)).when(icyReader).currentlyPlaying();
        Optional<Song> optionalSong = fromRadioService.search(new FromRadioRequest("validUrl"));
        assertTrue(optionalSong.isEmpty());
    }
}
