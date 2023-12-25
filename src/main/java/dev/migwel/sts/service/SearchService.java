package dev.migwel.sts.service;

import dev.migwel.sts.model.SearchRequest;
import dev.migwel.sts.model.Song;

import java.util.Optional;

public interface SearchService<T extends SearchRequest> {
    boolean isRelevant(Class<?> searchRequestType);

    Optional<Song> search(T searchRequest);
}
