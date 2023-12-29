package dev.migwel.sts.service;

import dev.migwel.sts.model.SaveRequest;
import dev.migwel.sts.model.Song;

public interface SaveService<T extends SaveRequest> {
    boolean isRelevant(Class<?> saveRequestType);

    void save(Song song, SaveRequest saveRequest);
}
