package dev.migwel.sts.spotify;

import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.ToResult;
import dev.migwel.sts.domain.model.ToSpotifyRequest;
import dev.migwel.sts.domain.service.ToService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Service
public class ToSpotifyService implements ToService<ToSpotifyRequest> {

    private static final Logger log = LogManager.getLogger(ToSpotifyService.class);

    private final SpotifyService spotifyService;

    public ToSpotifyService(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public boolean isRelevant(Class<?> saveRequestType) {
        return saveRequestType.isAssignableFrom(ToSpotifyRequest.class);
    }

    @Override
    public ToResult save(Song song, ToSpotifyRequest toRequest) {
        Optional<SpotifySong> searchResult = spotifyService.search(song);
        if (searchResult.isEmpty()) {
            log.info("Could not find title : " + song);
            return ToResult.failure("The title could not be found");
        }
        spotifyService.saveToPlaylist(searchResult.get().id());
        return ToResult.success();
    }
}
