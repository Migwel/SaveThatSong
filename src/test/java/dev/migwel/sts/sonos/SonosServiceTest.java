package dev.migwel.sts.sonos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import dev.migwel.sts.sonos.dto.Group;
import dev.migwel.sts.sonos.dto.PlaybackMetadataResponse;
import dev.migwel.sts.util.FileUtil;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

class SonosServiceTest {
    private static MockWebServer webServer;
    private static SonosService sonosService;

    @BeforeAll
    static void beforeAll() throws IOException {
        webServer = new MockWebServer();
        webServer.start();

        String webServerUrl = String.format("http://localhost:%s", webServer.getPort());
        WebClient webClient = WebClient.create(webServerUrl);
        SonosConfiguration sonosConfiguration = Mockito.mock(SonosConfiguration.class);
        when(sonosConfiguration.getControlBaseUrl()).thenReturn(webServerUrl);
        sonosService = new SonosService(sonosConfiguration, webClient);
    }

    @Test
    void getHouseholds_noHousehold() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/households_noHousehold.json"))
                        .addHeader("Content-Type", "application/json"));
        List<String> households = sonosService.getHouseholds();
        assertTrue(households.isEmpty());
    }

    @Test
    void getHouseholds_oneHousehold() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/households_oneHousehold.json"))
                        .addHeader("Content-Type", "application/json"));
        List<String> households = sonosService.getHouseholds();
        assertEquals(1, households.size());
        assertTrue(households.contains("id1"));
    }

    @Test
    void getHouseholds_multipleHouseholds() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/households_multipleHouseholds.json"))
                        .addHeader("Content-Type", "application/json"));
        List<String> households = sonosService.getHouseholds();
        assertEquals(2, households.size());
        assertTrue(households.contains("id1"));
        assertTrue(households.contains("id2"));
    }

    @Test
    void getHouseholds_non200Response() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setBody(FileUtil.loadFile("sonos/error.json"))
                        .addHeader("Content-Type", "application/json"));
        List<String> households = sonosService.getHouseholds();
        assertTrue(households.isEmpty());
    }

    @Test
    void getGroups_noGroup() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/groups_noGroup.json"))
                        .addHeader("Content-Type", "application/json"));
        List<Group> groups = sonosService.getGroups("householdId", null);
        assertTrue(groups.isEmpty());
    }

    @Test
    void getGroups_oneGroup() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/groups_oneGroup.json"))
                        .addHeader("Content-Type", "application/json"));
        List<Group> groups = sonosService.getGroups("householdId", null);
        assertEquals(1, groups.size());
        assertTrue(groups.stream().anyMatch(e -> "group:group1".equals(e.getId())));
    }

    @Test
    void getGroups_multipleGroups() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/groups_multipleGroups.json"))
                        .addHeader("Content-Type", "application/json"));
        List<Group> groups = sonosService.getGroups("householdId", null);
        assertEquals(2, groups.size());
        assertTrue(groups.stream().anyMatch(e -> "group:group1".equals(e.getId())));
        assertTrue(groups.stream().anyMatch(e -> "group:group2".equals(e.getId())));
    }

    @Test
    void getGroups_filterBasedOnPlaybackState() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/groups_multipleGroups.json"))
                        .addHeader("Content-Type", "application/json"));
        List<Group> groups =
                sonosService.getGroups("householdId", PlaybackState.PLAYBACK_STATE_PLAYING);
        assertEquals(1, groups.size());
        assertTrue(groups.stream().anyMatch(e -> "group:group1".equals(e.getId())));
        assertFalse(groups.stream().anyMatch(e -> "group:group2".equals(e.getId())));
    }

    @Test
    void getGroups_non200Response() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setBody(FileUtil.loadFile("sonos/error.json"))
                        .addHeader("Content-Type", "application/json"));
        List<Group> groups = sonosService.getGroups("householdId", null);
        assertTrue(groups.isEmpty());
    }

    @Test
    void getPlaybackMetadata_nothingPlaying() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/playbackMetadata_nothingPlaying.json"))
                        .addHeader("Content-Type", "application/json"));
        PlaybackMetadataResponse playbackMetadata = sonosService.getPlaybackMetadata("groupId");
        assertNotNull(playbackMetadata);
        assertNull(playbackMetadata.getCurrentItem());
        assertNull(playbackMetadata.getStreamInfo());
    }

    @Test
    void getPlaybackMetadata_withCurrentItem() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/playbackMetadata_currentItem.json"))
                        .addHeader("Content-Type", "application/json"));
        PlaybackMetadataResponse playbackMetadata = sonosService.getPlaybackMetadata("groupId");
        assertNotNull(playbackMetadata);
        assertNull(playbackMetadata.getStreamInfo());
        assertNotNull(playbackMetadata.getCurrentItem());
        assertNotNull(playbackMetadata.getCurrentItem().getTrack());
        assertEquals(
                "Jacques Brel", playbackMetadata.getCurrentItem().getTrack().getArtist().getName());
        assertEquals("Ne me quitte pas", playbackMetadata.getCurrentItem().getTrack().getName());
    }

    @Test
    void getPlaybackMetadata_withStreamInfo() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setBody(FileUtil.loadFile("sonos/playbackMetadata_streamInfo.json"))
                        .addHeader("Content-Type", "application/json"));
        PlaybackMetadataResponse playbackMetadata = sonosService.getPlaybackMetadata("groupId");
        assertNotNull(playbackMetadata);
        assertNull(playbackMetadata.getCurrentItem());
        assertEquals("The Cure - Friday I'm in love", playbackMetadata.getStreamInfo());
    }

    @Test
    void getPlaybackMetadata_non200Response() throws URISyntaxException, IOException {
        webServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setBody(FileUtil.loadFile("sonos/error.json"))
                        .addHeader("Content-Type", "application/json"));
        PlaybackMetadataResponse playbackMetadata = sonosService.getPlaybackMetadata("groupId");
        assertNull(playbackMetadata);
    }
}
