package dev.migwel.sts.controller;

import dev.migwel.sts.controller.dto.FromRadioRequest;
import dev.migwel.sts.controller.dto.FromRequest;
import dev.migwel.sts.controller.dto.FromSonosRequest;
import dev.migwel.sts.controller.dto.ToDatabaseRequest;
import dev.migwel.sts.controller.dto.ToRequest;
import dev.migwel.sts.controller.dto.ToSpotifyRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    private final Converter converter = new Converter();

    @Test
    void convert_returnsCorrectFromRequestClass() {
        FromRequest radioRequest = new FromRadioRequest("https://stream.url");
        FromRequest sonosRequest = new FromSonosRequest("1", "1");
        Assertions.assertEquals(
                dev.migwel.sts.domain.model.FromRadioRequest.class,
                converter.convert(radioRequest).getClass());
        Assertions.assertEquals(
                dev.migwel.sts.domain.model.FromSonosRequest.class,
                converter.convert(sonosRequest).getClass());
    }

    @Test
    void convert_returnsCorrectToRequestClass() {
        ToRequest databaseRequest = new ToDatabaseRequest();
        ToRequest spotifyRequest = new ToSpotifyRequest();
        Assertions.assertEquals(
                dev.migwel.sts.domain.model.ToDatabaseRequest.class,
                converter.convert(databaseRequest, "user").getClass());
        Assertions.assertEquals(
                dev.migwel.sts.domain.model.ToSpotifyRequest.class,
                converter.convert(spotifyRequest, "user").getClass());
    }

    @Test
    void convert_radioRequestDtoToDomain() {
        FromRadioRequest dtoRequest = new FromRadioRequest("https://stream.url");
        dev.migwel.sts.domain.model.FromRadioRequest domainRequest = converter.convert(dtoRequest);
        Assertions.assertEquals(dtoRequest.getUrl(), domainRequest.url());
    }

    @Test
    void convert_radioRequestDomainToDto() {
        dev.migwel.sts.domain.model.FromRadioRequest domainRequest =
                new dev.migwel.sts.domain.model.FromRadioRequest("https://stream.url");
        FromRadioRequest dtoRequest = converter.convert(domainRequest);
        Assertions.assertEquals(domainRequest.url(), dtoRequest.getUrl());
    }

    @Test
    void convert_sonosRequestDtoToDomain() {
        FromSonosRequest dtoRequest = new FromSonosRequest("1", "2");
        dev.migwel.sts.domain.model.FromSonosRequest domainRequest = converter.convert(dtoRequest);
        Assertions.assertEquals(dtoRequest.getHouseholdId(), domainRequest.householdId());
        Assertions.assertEquals(dtoRequest.getGroupId(), domainRequest.groupId());
    }
}
