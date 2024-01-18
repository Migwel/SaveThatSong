package dev.migwel.sts.sonos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.migwel.sts.domain.model.FromSonosRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.sonos.dto.Group;
import dev.migwel.sts.sonos.dto.PlaybackMetadataResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FromSonosServiceTest {

    @Mock SonosService sonosService;
    @Mock PlaybackMetadataParser playbackMetadataParser;
    @InjectMocks FromSonosService fromSonosService;

    @Test
    void search_noHousehold() {
        when(sonosService.getHouseholds()).thenReturn(Collections.emptyList());
        Optional<Song> song = fromSonosService.search(new FromSonosRequest(null, null));
        assertTrue(song.isEmpty());
    }

    @Test
    void search_noActiveGroup() {
        when(sonosService.getHouseholds()).thenReturn(List.of("householdId"));
        when(sonosService.getGroups(any(), any())).thenReturn(Collections.emptyList());
        Optional<Song> song = fromSonosService.search(new FromSonosRequest(null, null));
        assertTrue(song.isEmpty());
    }

    @Test
    void search_noSongFound() {
        when(sonosService.getHouseholds()).thenReturn(List.of("householdId"));
        when(sonosService.getGroups(any(), any()))
                .thenReturn(
                        List.of(
                                new Group(
                                        "groupdId",
                                        PlaybackState.PLAYBACK_STATE_PLAYING.name(),
                                        List.of("playerId"))));
        when(sonosService.getPlaybackMetadata(any())).thenReturn(null);
        when(playbackMetadataParser.parse(any())).thenReturn(Optional.empty());
        Optional<Song> song = fromSonosService.search(new FromSonosRequest(null, null));
        assertTrue(song.isEmpty());
    }

    @Test
    void search_songFound() {
        Song song = new Song("artist", "title", "artist - title");
        when(sonosService.getHouseholds()).thenReturn(List.of("householdId"));
        when(sonosService.getGroups(any(), any()))
                .thenReturn(
                        List.of(
                                new Group(
                                        "groupdId",
                                        PlaybackState.PLAYBACK_STATE_PLAYING.name(),
                                        List.of("playerId"))));
        when(sonosService.getPlaybackMetadata(any()))
                .thenReturn(new PlaybackMetadataResponse(null, song.rawData()));
        when(playbackMetadataParser.parse(any())).thenReturn(Optional.of(song));
        Optional<Song> foundSong = fromSonosService.search(new FromSonosRequest(null, null));
        assertTrue(foundSong.isPresent());
        assertEquals(song, foundSong.get());
    }

    @Test
    void search_dontSearchForHouseholdsIfProvided() {
        when(sonosService.getGroups(any(), any())).thenReturn(Collections.emptyList());
        fromSonosService.search(new FromSonosRequest("householdId", null));
        verify(sonosService, never()).getHouseholds();
    }

    @Test
    void search_dontSearchForGroupsIfProvided() {
        when(sonosService.getPlaybackMetadata(any())).thenReturn(null);
        fromSonosService.search(new FromSonosRequest(null, "groupId"));
        verify(sonosService, never()).getHouseholds();
        verify(sonosService, never()).getGroups(any(), any());
    }
}
