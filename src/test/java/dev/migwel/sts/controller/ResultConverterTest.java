package dev.migwel.sts.controller;

import dev.migwel.sts.controller.dto.SaveResponse;
import dev.migwel.sts.domain.model.SaveResult;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.ToResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultConverterTest {

    private final ResultConverter converter = new ResultConverter();

    @Test
    void saveResultconvert_saveResultNoSongFound() {
        SaveResult saveResult = SaveResult.noSongFound();
        SaveResponse response = converter.convert(saveResult);
        assertNull(response.song());
        assertNull(response.toResult());
    }

    @Test
    void saveResultconvert_errorToAction() {
        Song song = new Song("artist", "title", "artist - title");
        SaveResult saveResult = new SaveResult(song, ToResult.failure("errorMessage"));
        SaveResponse response = converter.convert(saveResult);

        assertNotNull(response.song());
        assertEquals(saveResult.getSong().artist(), response.song().artist());
        assertEquals(saveResult.getSong().title(), response.song().title());
        assertEquals(saveResult.getSong().rawData(), response.song().rawData());

        assertNotNull(response.toResult());
        assertFalse(response.toResult().success());
        assertEquals(
                saveResult.getToResult().getErrorMessage(), response.toResult().errorMessage());
    }

    @Test
    void saveResultconvert_saveResultSuccess() {
        Song song = new Song("artist", "title", "artist - title");
        SaveResult saveResult = new SaveResult(song, ToResult.success());
        SaveResponse response = converter.convert(saveResult);

        assertNotNull(response.song());
        assertEquals(response.song().artist(), saveResult.getSong().artist());
        assertEquals(response.song().title(), saveResult.getSong().title());
        assertEquals(response.song().rawData(), saveResult.getSong().rawData());

        assertNotNull(response.toResult());
        assertTrue(response.toResult().success());
        assertNull(response.toResult().errorMessage());
    }
}
