package dev.migwel.sts.radio;

import dev.migwel.sts.model.RadioSearchRequest;
import dev.migwel.sts.model.Song;
import dev.migwel.sts.service.SearchService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RadioSearchService implements SearchService<RadioSearchRequest> {

    @Override
    public boolean isRelevant(Class<?> searchRequestType) {
        return searchRequestType.isAssignableFrom(RadioSearchRequest.class);
    }

    @Override
    public Optional<Song> search(RadioSearchRequest searchRequest) {
        return Optional.empty();
    }
}
