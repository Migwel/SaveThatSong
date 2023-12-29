package dev.migwel.sts.sonos;

import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.FromSonosRequest;
import dev.migwel.sts.domain.service.FromService;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FromSonosService implements FromService<FromSonosRequest> {
    @Override
    public boolean isRelevant(Class<?> searchRequestType) {
        return searchRequestType.isAssignableFrom(FromSonosRequest.class);
    }

    @Override
    public Optional<Song> search(FromSonosRequest searchRequest) {
        return Optional.empty();
    }
}
