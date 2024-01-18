package dev.migwel.sts.sonos;

import dev.migwel.sts.domain.model.FromSonosRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.service.FromService;
import dev.migwel.sts.sonos.dto.Group;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FromSonosService implements FromService<FromSonosRequest> {

    private final SonosService sonosService;
    private final PlaybackMetadataParser playbackMetadataParser;

    public FromSonosService(
            SonosService sonosService, PlaybackMetadataParser playbackMetadataParser) {
        this.sonosService = sonosService;
        this.playbackMetadataParser = playbackMetadataParser;
    }

    @Override
    public boolean isRelevant(Class<?> searchRequestType) {
        return searchRequestType.isAssignableFrom(FromSonosRequest.class);
    }

    @Override
    public Optional<Song> search(FromSonosRequest searchRequest) {
        Optional<Group> activeGroup = getActiveGroup();
        if (activeGroup.isEmpty()) {
            return Optional.empty();
        }
        return whatsPlaying(activeGroup.get().getId());
    }

    private Optional<Song> whatsPlaying(String groupId) {
        var playbackMetadataResponse = sonosService.getPlaybackMetadata(groupId);
        return playbackMetadataParser.parse(playbackMetadataResponse);
    }

    private Optional<Group> getActiveGroup() {
        List<String> households = sonosService.getHouseholds();
        for (String household : households) {
            List<Group> activeGroups =
                    sonosService.getGroups(household, PlaybackState.PLAYBACK_STATE_PLAYING);
            if (!activeGroups.isEmpty()) {
                return Optional.of(activeGroups.getFirst());
            }
        }
        return Optional.empty();
    }
}
