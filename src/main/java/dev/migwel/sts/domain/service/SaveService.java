package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.FromRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.ToRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaveService {

    private static final Logger logger = LogManager.getLogger(SaveService.class);
    private final FromServiceFactory fromServiceFactory;
    private final ToServiceFactory toServiceFactory;

    public SaveService(FromServiceFactory fromServiceFactory, ToServiceFactory toServiceFactory) {
        this.fromServiceFactory = fromServiceFactory;
        this.toServiceFactory = toServiceFactory;
    }

    public <T extends FromRequest, U extends ToRequest> void save(T from, U to) {
        FromService<T> fromService = fromServiceFactory.getFromService(from);
        Optional<Song> optionalSong = fromService.search(from);
        if (optionalSong.isEmpty()) {
            logger.info("Could not find what's playing for " + from);
            return;
        }
        ToService<U> toService = toServiceFactory.getToService(to);
        toService.save(optionalSong.get(), to);
    }
}
