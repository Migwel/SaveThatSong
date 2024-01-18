package dev.migwel.sts.sonos;

import dev.migwel.sts.sonos.dto.Group;
import dev.migwel.sts.sonos.dto.GroupsResponse;
import dev.migwel.sts.sonos.dto.Household;
import dev.migwel.sts.sonos.dto.HouseholdsResponse;
import dev.migwel.sts.sonos.dto.PlaybackMetadataResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Service
public class SonosService {
    private static final Logger log = LogManager.getLogger(SonosService.class);

    private final WebClient webClient;
    private final SonosConfiguration sonosConfiguration;

    public SonosService(SonosConfiguration sonosConfiguration, WebClient webClient) {
        this.webClient = webClient;
        this.sonosConfiguration = sonosConfiguration;
    }

    public List<String> getHouseholds() {
        HouseholdsResponse response;
        try {
            response =
                    webClient
                            .get()
                            .uri(URI.create(sonosConfiguration.getControlBaseUrl() + "/households"))
                            .attributes(
                                    ServletOAuth2AuthorizedClientExchangeFilterFunction
                                            .clientRegistrationId("sonos"))
                            .retrieve()
                            .bodyToMono(HouseholdsResponse.class)
                            .block();
        } catch (RuntimeException e) {
            log.warn("An error occurrer while fetching households", e);
            return Collections.emptyList();
        }
        if (response == null
                || response.getHouseholds() == null
                || response.getHouseholds().isEmpty()) {
            return Collections.emptyList();
        }
        return response.getHouseholds().stream().map(Household::getId).toList();
    }

    public List<Group> getGroups(String householdId, @CheckForNull PlaybackState playbackState) {
        GroupsResponse response;
        try {
            response =
                    webClient
                            .get()
                            .uri(
                                    URI.create(
                                            String.format(
                                                    sonosConfiguration.getControlBaseUrl()
                                                            + "/households/%s/groups",
                                                    householdId)))
                            .attributes(
                                    ServletOAuth2AuthorizedClientExchangeFilterFunction
                                            .clientRegistrationId("sonos"))
                            .retrieve()
                            .bodyToMono(GroupsResponse.class)
                            .block();
        } catch (RuntimeException e) {
            log.warn("An error occurrer while fetching groups for household " + householdId, e);
            return Collections.emptyList();
        }
        if (response == null || response.getGroups() == null || response.getGroups().isEmpty()) {
            log.warn("No group could be found for household " + householdId);
            return Collections.emptyList();
        }
        if (playbackState == null) {
            return response.getGroups();
        }
        return response.getGroups().stream()
                .filter(e -> playbackState.name().equals(e.getPlaybackState()))
                .toList();
    }

    @CheckForNull
    public PlaybackMetadataResponse getPlaybackMetadata(String groupId) {
        PlaybackMetadataResponse playbackMetadata;
        try {
            playbackMetadata =
                    webClient
                            .get()
                            .uri(
                                    URI.create(
                                            String.format(
                                                    sonosConfiguration.getControlBaseUrl()
                                                            + "/groups/%s/playbackMetadata",
                                                    groupId)))
                            .attributes(
                                    ServletOAuth2AuthorizedClientExchangeFilterFunction
                                            .clientRegistrationId("sonos"))
                            .retrieve()
                            .bodyToMono(PlaybackMetadataResponse.class)
                            .block();
        } catch (RuntimeException e) {
            log.warn("An error occurrer while playback metadata for group " + groupId, e);
            return null;
        }
        return playbackMetadata;
    }
}
