package dev.migwel.sts.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.migwel.sts.domain.model.FromRadioRequest;
import dev.migwel.sts.domain.model.FromRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.ToDatabaseRequest;
import dev.migwel.sts.domain.model.ToRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SaveServiceTest {

    @Mock private FromServiceFactory fromServiceFactory;
    @Mock private ToServiceFactory toServiceFactory;
    @Mock private FromService<?> fromService;
    @Mock private ToService<?> toService;
    @InjectMocks private SaveService saveService;

    @BeforeEach
    private void before() {
        doReturn(fromService).when(fromServiceFactory).getFromService(any());
        lenient().doReturn(toService).when(toServiceFactory).getToService(any());
    }

    @Test
    void save_noSongFound() {
        doReturn(Optional.empty()).when(fromService).search(any());
        FromRequest from = new FromRadioRequest("https://radio.url");
        ToRequest to = new ToDatabaseRequest();
        saveService.save(from, to);
        verify(toService, never()).save(any(), any());
    }

    @Test
    void save_songFound() {
        Song song = new Song("artist", "title", "rawData");
        doReturn(Optional.of(song)).when(fromService).search(any());
        FromRequest from = new FromRadioRequest("https://radio.url");
        ToRequest to = new ToDatabaseRequest();
        saveService.save(from, to);
        verify(toService, times(1)).save(eq(song), any());
    }
}
