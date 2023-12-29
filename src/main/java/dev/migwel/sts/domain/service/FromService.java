package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.FromRequest;
import dev.migwel.sts.domain.model.Song;

import java.util.Optional;

public interface FromService<T extends FromRequest> {
    boolean isRelevant(Class<?> searchRequestType);

    Optional<Song> search(T searchRequest);
}
