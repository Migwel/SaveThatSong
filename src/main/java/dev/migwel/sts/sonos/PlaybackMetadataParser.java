package dev.migwel.sts.sonos;

import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.sonos.dto.PlaybackMetadataResponse;
import dev.migwel.sts.sonos.dto.QueueItem;
import dev.migwel.sts.sonos.dto.Track;
import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;
import java.util.Optional;

@Component
public class PlaybackMetadataParser {
    public Optional<Song> parse(@CheckForNull PlaybackMetadataResponse playbackMetadataResponse) {
        if (playbackMetadataResponse == null) {
            return Optional.empty();
        }
        if (playbackMetadataResponse.getCurrentItem() != null) {
            Optional<Song> song = parse(playbackMetadataResponse.getCurrentItem());
            if (song.isPresent()) {
                return song;
            }
        }
        if (playbackMetadataResponse.getStreamInfo() != null) {
            return parse(playbackMetadataResponse.getStreamInfo());
        }
        return Optional.empty();
    }

    private Optional<Song> parse(String streamInfo) {
        String[] streamInfoParts = streamInfo.split("-");
        if (streamInfoParts.length <= 1) {
            return Optional.of(new Song("artistUnknown", "titleUnknown", streamInfo));
        }
        return Optional.of(
                new Song(streamInfoParts[0].trim(), streamInfoParts[1].trim(), streamInfo));
    }

    private Optional<Song> parse(QueueItem currentItem) {
        Track track = currentItem.getTrack();
        if (track == null) {
            return Optional.empty();
        }
        String title = track.getName();
        String artist = track.getArtist() != null ? track.getArtist().getName() : null;
        return Optional.of(new Song(artist, title, artist + " - " + title));
    }
}
