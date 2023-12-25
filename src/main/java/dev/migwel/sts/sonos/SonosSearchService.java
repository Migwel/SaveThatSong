package dev.migwel.sts.sonos;

import dev.migwel.sts.model.Song;
import dev.migwel.sts.model.SonosSearchRequest;
import dev.migwel.sts.service.SearchService;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SonosSearchService implements SearchService<SonosSearchRequest> {
    @Override
    public boolean isRelevant(Class<?> searchRequestType) {
        return searchRequestType.isAssignableFrom(SonosSearchRequest.class);
    }

    @Override
    public Optional<Song> search(SonosSearchRequest searchRequest) {
        return Optional.empty();
    }
}
