package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.ToRequest;
import dev.migwel.sts.domain.model.Song;

public interface ToService<T extends ToRequest> {
    boolean isRelevant(Class<?> saveRequestType);

    void save(Song song, ToRequest toRequest);
}
