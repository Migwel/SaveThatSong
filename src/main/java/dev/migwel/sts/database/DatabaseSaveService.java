package dev.migwel.sts.database;

import dev.migwel.sts.database.entities.Converter;
import dev.migwel.sts.exception.SaveToException;
import dev.migwel.sts.model.PersistSaveRequest;
import dev.migwel.sts.model.SaveRequest;
import dev.migwel.sts.model.Song;
import dev.migwel.sts.service.SaveService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Service
public class DatabaseSaveService implements SaveService<PersistSaveRequest> {
    private static final Logger log = LogManager.getLogger(DatabaseSaveService.class);

    private final Converter converter;
    private final SongRepository songRepository;

    public DatabaseSaveService(Converter converter, SongRepository songRepository) {
        this.converter = converter;
        this.songRepository = songRepository;
    }

    @Override
    public boolean isRelevant(Class<?> saveRequestType) {
        return saveRequestType.isAssignableFrom(PersistSaveRequest.class);
    }

    @Override
    public void save(Song song, SaveRequest saveRequest) {
        dev.migwel.sts.database.entities.Song entitySong = converter.convert(song);
        try {
            songRepository.save(entitySong);
        } catch (DataAccessException e) {
            log.warn("Could not persist song", e);
            throw new SaveToException("Cold not persist song: " + e.getMessage());
        }
    }
}
