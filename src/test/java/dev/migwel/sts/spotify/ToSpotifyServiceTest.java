package dev.migwel.sts.spotify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.ToSpotifyRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ToSpotifyServiceTest {

    @Mock private SpotifyService spotifyService;
    @InjectMocks private ToSpotifyService toSpotifyService;

    @Test
    void save_songCouldNotBeFound() {
        when(spotifyService.search(any())).thenReturn(Optional.empty());
        Song song = new Song("artist", "title", "artist - title");
        toSpotifyService.save(song, new ToSpotifyRequest());
        verify(spotifyService, never()).saveToPlaylist(any());
    }

    @Test
    void save_songCouldBeFound() {
        when(spotifyService.search(any())).thenReturn(Optional.of(new SpotifySong("trackId")));
        Song song = new Song("artist", "title", "artist - title");
        toSpotifyService.save(song, new ToSpotifyRequest());
        verify(spotifyService, times(1)).saveToPlaylist(any());
    }
}
