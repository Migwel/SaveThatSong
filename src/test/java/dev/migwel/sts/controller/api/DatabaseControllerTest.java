package dev.migwel.sts.controller.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.migwel.sts.SecurityConfig;
import dev.migwel.sts.database.DatabaseService;
import dev.migwel.sts.domain.model.PersistedSong;
import dev.migwel.sts.domain.model.Song;

import dev.migwel.sts.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Import(value = {SecurityConfig.class, Converter.class, ResultConverter.class})
@AutoConfigureTestDatabase
@WebMvcTest(DatabaseController.class)
class DatabaseControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ResultConverter resultConverter;
    @MockBean private DatabaseService databaseService;

    @Test
    @WithAnonymousUser
    void getSongs_withoutAuthenticatedUser() throws Exception {
        this.mockMvc.perform(get("/api/database/songs")).andExpect(status().isUnauthorized());
        ;
    }

    @Test
    @WithMockUser
    void getSongs_noSongFound() throws Exception {
        when(databaseService.getSongs(any(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        this.mockMvc
                .perform(get("/api/database/songs"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("{\"songs\":[]}"));
    }

    @Test
    @WithMockUser
    void getSongs_songsFound() throws Exception {
        when(databaseService.getSongs(any(), anyInt(), anyInt()))
                .thenReturn(
                        List.of(
                                new PersistedSong(
                                        new Song("artist", "title", "artist - title"),
                                        "username",
                                        Date.from(Instant.parse("2023-01-01T10:00:00.00Z"))),
                                new PersistedSong(
                                        new Song("artist", "otherTitle", "artist - otherTitle"),
                                        "username",
                                        Date.from(Instant.parse("2023-01-02T10:00:00.00Z")))));
        this.mockMvc
                .perform(get("/api/database/songs"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        FileUtil.loadFile(
                                                "database/getSongs_songsFound_response.json")));
    }

    @Test
    @WithMockUser
    void getSongs_noLimitOrPageProvided() throws Exception {
        this.mockMvc.perform(get("/api/database/songs")).andExpect(status().isNoContent());
        verify(databaseService).getSongs(any(), intThat(e -> e > 0), eq(0));
    }

    @Test
    @WithMockUser
    void getSongs_limitAndPageProvided() throws Exception {
        int limit = 25;
        int page = 2;
        this.mockMvc
                .perform(
                        get("/api/database/songs")
                                .param("limit", Integer.toString(limit))
                                .param("page", Integer.toString(page)))
                .andExpect(status().isNoContent());
        verify(databaseService).getSongs(any(), eq(limit), eq(page));
    }
}
