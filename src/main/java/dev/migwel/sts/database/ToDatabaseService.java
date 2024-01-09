package dev.migwel.sts.database;

import dev.migwel.sts.database.entities.Converter;
import dev.migwel.sts.domain.exception.SaveToException;
import dev.migwel.sts.domain.model.ToDatabaseRequest;
import dev.migwel.sts.domain.model.ToRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.service.ToService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Service
public class ToDatabaseService implements ToService<ToDatabaseRequest> {
    private static final Logger log = LogManager.getLogger(ToDatabaseService.class);

    private final Converter converter;
    private final SongRepository songRepository;

    public ToDatabaseService(Converter converter, SongRepository songRepository) {
        this.converter = converter;
        this.songRepository = songRepository;
    }

    @Override
    public boolean isRelevant(Class<?> saveRequestType) {
        return saveRequestType.isAssignableFrom(ToDatabaseRequest.class);
    }

    @Override
    public void save(Song song, ToDatabaseRequest toRequest) {
        dev.migwel.sts.database.entities.Song entitySong =
                converter.convert(toRequest.getUsername(), song);
        try {
            songRepository.save(entitySong);
        } catch (DataAccessException e) {
            log.warn("Could not persist song", e);
            throw new SaveToException("Cold not persist song: " + e.getMessage());
        }
    }
}
