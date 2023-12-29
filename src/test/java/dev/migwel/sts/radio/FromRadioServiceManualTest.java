package dev.migwel.sts.radio;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.domain.model.FromRadioRequest;
import dev.migwel.sts.domain.model.Song;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class FromRadioServiceManualTest {

    private final FromRadioService fromRadioService = new FromRadioService(new IcyReaderProvider());

    @Test
    void search_NPORadio2() {
        Optional<Song> optionalSong =
                fromRadioService.search(
                        new FromRadioRequest("https://icecast.omroep.nl/radio2-bb-mp3"));
        assertTrue(optionalSong.isPresent());
        System.out.println(optionalSong.get());
    }
}
