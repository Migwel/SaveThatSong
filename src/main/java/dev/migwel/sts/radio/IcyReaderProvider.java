package dev.migwel.sts.radio;

import dev.migwel.icyreader.IcyReader;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@Component
@ParametersAreNonnullByDefault
class IcyReaderProvider {

    IcyReader getIcyReader(String url) {
        return new IcyReader.IcyReaderBuilder(url).build();
    }
}
