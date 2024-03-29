package dev.migwel.sts.controller.api;

import dev.migwel.sts.controller.api.dto.SaveResponse;
import dev.migwel.sts.controller.api.dto.Song;
import dev.migwel.sts.controller.api.dto.ToResult;
import dev.migwel.sts.controller.api.dto.database.PersistedSong;
import dev.migwel.sts.domain.model.SaveResult;

import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Component
@ParametersAreNonnullByDefault
public class ResultConverter {
    public SaveResponse convert(SaveResult saveResult) {
        return new SaveResponse(convert(saveResult.getSong()), convert(saveResult.getToResult()));
    }

    private ToResult convert(@CheckForNull dev.migwel.sts.domain.model.ToResult toResult) {
        if (toResult == null) {
            return null;
        }
        return new ToResult(toResult.isSuccess(), toResult.getErrorMessage());
    }

    public Song convert(@CheckForNull dev.migwel.sts.domain.model.Song song) {
        if (song == null) {
            return null;
        }
        return new Song(song.artist(), song.title(), song.rawData());
    }

    @Nonnull
    public PersistedSong convert(@Nonnull dev.migwel.sts.domain.model.PersistedSong persistedSong) {
        return new PersistedSong(convert(persistedSong.song()), persistedSong.creationDate());
    }
}
