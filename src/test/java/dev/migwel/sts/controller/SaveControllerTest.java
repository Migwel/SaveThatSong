package dev.migwel.sts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.migwel.sts.domain.service.SaveService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SaveController.class)
class SaveControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private Converter converter;
    @MockBean private SaveService service;

    @Test
    void save_emptyRequest() throws Exception {
        this.mockMvc
                .perform(post("/save"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .string(
                                        "{\"fieldErrors\":[{\"field\":\"\",\"message\":\"Message could not be read\"}]}"));
    }

    @Test
    void save_incorrectRequestRadioWithoutUrl() throws Exception {
        this.mockMvc
                .perform(
                        post("/save")
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
    void save_incorrectType() throws Exception {
        this.mockMvc
                .perform(
                        post("/save")
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
    void save_validRequest() throws Exception {
        this.mockMvc
                .perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"from\":{\"type\":\"RADIO\", \"url\":\"https://stream.url\"}, \"to\":{\"type\": \"DATABASE\"}}'"))
                .andExpect(status().isOk());
    }
}
