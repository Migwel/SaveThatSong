package dev.migwel.sts.spotify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.util.FileUtil;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.util.Optional;

class SpotifyServiceTest {
    private static MockWebServer webServer;
    private static SpotifyService spotifyService;

    @BeforeAll
    static void beforeAll() throws IOException {
        webServer = new MockWebServer();
        webServer.start();

        String webServerUrl = String.format("http://localhost:%s", webServer.getPort());
        WebClient webClient = WebClient.create(webServerUrl);
        SpotifyConfiguration spotifyConfiguration = Mockito.mock(SpotifyConfiguration.class);
        when(spotifyConfiguration.getSaveUrl()).thenReturn(webServerUrl);
        when(spotifyConfiguration.getSearchUrl()).thenReturn(webServerUrl);
        spotifyService = new SpotifyService(spotifyConfiguration, webClient);
    }

    @Test
    void search_nothingFound() throws Exception {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("spotify/search_noMatch.json"))
                        .addHeader("Content-Type", "application/json"));
        Optional<SpotifySong> search =
                spotifyService.search(new Song("artist", "title", "artist-title"));
        assertTrue(search.isEmpty());
    }

    @Test
    void search_songExists() throws Exception {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("spotify/search_oneMatch.json"))
                        .addHeader("Content-Type", "application/json"));
        Optional<SpotifySong> search =
                spotifyService.search(
                        new Song(
                                "John Coltrane",
                                "My Favorite Things",
                                "John Coltrane - My Favorite Things"));
        assertTrue(search.isPresent());
    }

    @Test
    void save_incorrectTrack() throws Exception {
        webServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setBody(FileUtil.loadFile("spotify/bad_request.json"))
                        .addHeader("Content-Type", "application/json"));
        assertThrows(
                WebClientResponseException.class,
                () -> spotifyService.saveToPlaylist("invalidTrackId"));
    }

    @Test
    void save_validTrack() {
        webServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-Type", "application/json"));
        try {
            spotifyService.saveToPlaylist("validTrackId");
        } catch (RuntimeException e) {
            fail();
        }
    }

    @AfterAll
    static void afterAll() throws IOException {
        webServer.shutdown();
    }
}
