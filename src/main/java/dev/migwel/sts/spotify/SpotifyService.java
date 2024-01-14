package dev.migwel.sts.spotify;

import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.spotify.dto.SearchResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Service
public class SpotifyService {
    private final SpotifyConfiguration spotifyConfiguration;
    private final WebClient webClient;

    @Autowired
    public SpotifyService(SpotifyConfiguration spotifyConfiguration, WebClient webClient) {
        this.spotifyConfiguration = spotifyConfiguration;
        this.webClient = webClient;
    }

    public Optional<SpotifySong> search(Song song) {
        String query = buildSearchQuery(song);
        String url =
                String.format(
                        spotifyConfiguration.getSearchUrl(),
                        URLEncoder.encode(query, StandardCharsets.UTF_8));
        SearchResponse response =
                webClient
                        .get()
                        .uri(URI.create(url))
                        .attributes(
                                ServletOAuth2AuthorizedClientExchangeFilterFunction
                                        .clientRegistrationId("spotify"))
                        .retrieve()
                        .bodyToMono(SearchResponse.class)
                        .block();
        if (noTrackFound(response)) {
            return Optional.empty();
        }
        return Optional.of(new SpotifySong(response.getTracks().getItems().getFirst().getId()));
    }

    private boolean noTrackFound(@CheckForNull SearchResponse response) {
        return response == null
                || response.getTracks() == null
                || response.getTracks().getItems() == null
                || response.getTracks().getItems().isEmpty();
    }

    private String buildSearchQuery(Song song) {
        return "artist:" + song.artist() + " track:" + song.title();
    }

    public void saveToPlaylist(String trackId) {
        webClient
                .put()
                .uri(URI.create(spotifyConfiguration.getSaveUrl()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(
                        Mono.just(new SpotifySaveTrackRequest(trackId)),
                        SpotifySaveTrackRequest.class)
                .attributes(
                        ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(
                                "spotify"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private static class SpotifySaveTrackRequest {
        private final List<String> ids;

        public SpotifySaveTrackRequest(String id) {
            this.ids = List.of(id);
        }

        public List<String> getIds() {
            return ids;
        }
    }
}
