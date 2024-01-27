package dev.migwel.sts.database;

import dev.migwel.sts.database.entities.Converter;
import dev.migwel.sts.domain.model.PersistedSong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Service
@ParametersAreNonnullByDefault
public class DatabaseService {

    private final SongRepository songRepository;
    private final Converter converter;
    private static final int MAX_LIMIT = 50;

    @Autowired
    public DatabaseService(SongRepository songRepository, Converter converter) {
        this.songRepository = songRepository;
        this.converter = converter;
    }

    @Nonnull
    public List<PersistedSong> getSongs(String username, int limit, int page) {
        if (page < 0) {
            throw new IllegalArgumentException("page cannot be negative");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("limit cannot be negative");
        }
        List<dev.migwel.sts.database.entities.Song> songs =
                songRepository.findByUsername(username, Math.min(limit, MAX_LIMIT), page);
        return songs.stream()
                .map(converter::convertToPersistedSong)
                .sorted(Comparator.comparing(PersistedSong::creationDate))
                .toList();
    }
}
