package dev.migwel.sts.sonos;

import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.sonos.dto.Artist;
import dev.migwel.sts.sonos.dto.PlaybackMetadataResponse;
import dev.migwel.sts.sonos.dto.QueueItem;
import dev.migwel.sts.sonos.dto.Track;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlaybackMetadataParserTest {

    private final PlaybackMetadataParser parser = new PlaybackMetadataParser();

    @Test
    void parse_nullPlaybackMetadata() {
        Optional<Song> song = parser.parse(null);
        assertTrue(song.isEmpty());
    }

    @Test
    void parse_emptyPlaybackMetadata() {
        Optional<Song> song = parser.parse(new PlaybackMetadataResponse(null, null));
        assertTrue(song.isEmpty());
    }

    @Test
    void parse_responseWithQueueItem() {
        Optional<Song> song =
                parser.parse(
                        new PlaybackMetadataResponse(
                                new QueueItem(new Track("title", new Artist("artist"))), null));
        assertTrue(song.isPresent());
        assertEquals("artist", song.get().artist());
        assertEquals("title", song.get().title());
    }

    @Test
    void parse_responseWithStreamInfo() {
        Optional<Song> song = parser.parse(new PlaybackMetadataResponse(null, "artist - title"));
        assertTrue(song.isPresent());
        assertEquals("artist", song.get().artist());
        assertEquals("title", song.get().title());
    }

    @Test
    void parse_responseWithWronglyFormattedStreamInfo() {
        String streamInfo = "artist / title";
        Optional<Song> song = parser.parse(new PlaybackMetadataResponse(null, streamInfo));
        assertTrue(song.isPresent());
        assertEquals("artistUnknown", song.get().artist());
        assertEquals("titleUnknown", song.get().title());
        assertEquals(streamInfo, song.get().rawData());
    }

    @Test
    void parse_responseWithIncorrectQueueItemAndCorrectStreamInfo() {
        Optional<Song> song =
                parser.parse(new PlaybackMetadataResponse(new QueueItem(null), "artist - title"));
        assertTrue(song.isPresent());
        assertEquals("artist", song.get().artist());
        assertEquals("title", song.get().title());
    }

    @Test
    void parse_responseWithQueueItemAndStreamInfo() {
        Optional<Song> song =
                parser.parse(
                        new PlaybackMetadataResponse(
                                new QueueItem(new Track("title", new Artist("artist"))),
                                "anotherArtist - anotherTitle"));
        assertTrue(song.isPresent());
        assertEquals("artist", song.get().artist());
        assertEquals("title", song.get().title());
    }
}
