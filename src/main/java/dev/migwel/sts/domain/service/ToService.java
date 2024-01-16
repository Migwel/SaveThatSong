package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.ToRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.ToResult;

public interface ToService<T extends ToRequest> {
    boolean isRelevant(Class<?> saveRequestType);

    ToResult save(Song song, T toRequest);
}
