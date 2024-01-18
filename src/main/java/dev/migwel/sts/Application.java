package dev.migwel.sts;

import dev.migwel.sts.sonos.SonosConfiguration;
import dev.migwel.sts.spotify.SpotifyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {SpotifyConfiguration.class, SonosConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
