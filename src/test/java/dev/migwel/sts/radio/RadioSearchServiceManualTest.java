package dev.migwel.sts.radio;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.model.RadioSearchRequest;
import dev.migwel.sts.model.Song;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class RadioSearchServiceManualTest {

    private final RadioSearchService radioSearchService =
            new RadioSearchService(new IcyReaderProvider());

    @Test
    void search_NPORadio2() {
        Optional<Song> optionalSong =
                radioSearchService.search(
                        new RadioSearchRequest("https://icecast.omroep.nl/radio2-bb-mp3"));
        assertTrue(optionalSong.isPresent());
        System.out.println(optionalSong.get());
    }
}
