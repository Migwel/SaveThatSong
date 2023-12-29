package dev.migwel.sts.radio;

import dev.migwel.icyreader.IcyReader;
import dev.migwel.icyreader.SongInfo;
import dev.migwel.sts.domain.model.FromRadioRequest;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.service.FromService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.annotation.CheckForNull;

@Service
public class FromRadioService implements FromService<FromRadioRequest> {

    private final IcyReaderProvider icyReaderProvider;

    @Autowired
    public FromRadioService(IcyReaderProvider icyReaderProvider) {
        this.icyReaderProvider = icyReaderProvider;
    }

    @Override
    public boolean isRelevant(Class<?> searchRequestType) {
        return searchRequestType.isAssignableFrom(FromRadioRequest.class);
    }

    @Override
    public Optional<Song> search(FromRadioRequest searchRequest) {
        IcyReader reader = icyReaderProvider.getIcyReader(searchRequest.url());
        return convert(reader.currentlyPlaying());
    }

    private Optional<Song> convert(@CheckForNull SongInfo songInfo) {
        if (songInfo == null) {
            return Optional.empty();
        }
        if (songInfo.artist() == null && songInfo.title() == null) {
            return Optional.empty();
        }
        return Optional.of(new Song(songInfo.artist(), songInfo.title(), songInfo.rawData()));
    }
}
