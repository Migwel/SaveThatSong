package dev.migwel.sts.controller.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromRadioRequestTest {

    @Test
    void deserialize() throws JsonProcessingException {
        String json = "{\"type\":\"RADIO\", \"url\":\"https://stream.url\"}";

        FromRequest from = new ObjectMapper().readerFor(FromRequest.class).readValue(json);

        assertEquals(FromRadioRequest.class, from.getClass());
    }
}
