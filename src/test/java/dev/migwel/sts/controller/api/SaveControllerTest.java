package dev.migwel.sts.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.migwel.sts.SecurityConfig;
import dev.migwel.sts.domain.model.SaveResult;
import dev.migwel.sts.domain.model.Song;
import dev.migwel.sts.domain.model.ToResult;
import dev.migwel.sts.domain.service.SaveService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Import(value = {SecurityConfig.class, Converter.class, ResultConverter.class})
@AutoConfigureTestDatabase
@WebMvcTest(SaveController.class)
class SaveControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private Converter converter;
    @Autowired private ResultConverter resultConverter;
    @MockBean private SaveService saveService;

    @Test
    @WithMockUser
    void save_emptyRequest() throws Exception {
        this.mockMvc
                .perform(post("/api/save"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .string(
                                        "{\"fieldErrors\":[{\"field\":\"\",\"message\":\"Message could not be read\"}]}"));
    }

    @Test
    @WithMockUser
    void save_incorrectRequestRadioWithoutUrl() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"from\":{\"type\":\"RADIO\"}, \"to\":{\"type\": \"DATABASE\"}}'"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .string(
                                        "{\"fieldErrors\":[{\"field\":\"from.url\",\"message\":\"must not be blank\"}]}"));
    }

    @Test
    @WithMockUser
    void save_incorrectType() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"from\":{\"type\":\"INCORRECT\"}, \"to\":{\"type\": \"DATABASE\"}}'"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .string(
                                        "{\"fieldErrors\":[{\"field\":\"from.type\",\"message\":\"Invalid type provided\"}]}"));
    }

    @Test
    @WithMockUser
    void save_validRequest() throws Exception {
        when(saveService.save(any(), any()))
                .thenReturn(
                        new SaveResult(
                                new Song("artist", "title", "artist - title"), ToResult.success()));
        this.mockMvc
                .perform(
                        post("/api/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"from\":{\"type\":\"RADIO\", \"url\":\"https://stream.url\"}, \"to\":{\"type\": \"DATABASE\"}}'"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void save_noSongFound() throws Exception {
        when(saveService.save(any(), any())).thenReturn(SaveResult.noSongFound());
        this.mockMvc
                .perform(
                        post("/api/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"from\":{\"type\":\"RADIO\", \"url\":\"https://stream.url\"}, \"to\":{\"type\": \"DATABASE\"}}'"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithAnonymousUser
    void save_withoutAuthenticatedUser() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"from\":{\"type\":\"RADIO\", \"url\":\"https://stream.url\"}, \"to\":{\"type\": \"DATABASE\"}}'"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void save_errorWhenPersisting() throws Exception {
        when(saveService.save(any(), any()))
                .thenReturn(
                        new SaveResult(
                                new Song("artist", "title", "artist - title"),
                                ToResult.failure("errorMessage")));
        this.mockMvc
                .perform(
                        post("/api/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"from\":{\"type\":\"RADIO\", \"url\":\"https://stream.url\"}, \"to\":{\"type\": \"DATABASE\"}}'"))
                .andExpect(status().isInternalServerError())
                .andExpect(
                        content()
                                .string(
                                        "{\"song\":{\"artist\":\"artist\",\"title\":\"title\",\"rawData\":\"artist - title\"},\"toResult\":{\"success\":false,\"errorMessage\":\"errorMessage\"}}"));
    }
}
