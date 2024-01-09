package dev.migwel.sts.controller;

import dev.migwel.sts.controller.dto.FromRadioRequest;
import dev.migwel.sts.controller.dto.FromRequest;
import dev.migwel.sts.controller.dto.FromSonosRequest;
import dev.migwel.sts.controller.dto.ToDatabaseRequest;
import dev.migwel.sts.controller.dto.ToRequest;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@Component("DTOConverter")
@ParametersAreNonnullByDefault
public class Converter {

    dev.migwel.sts.domain.model.FromRequest convert(FromRequest dto) {
        return switch (dto) {
            case FromRadioRequest fromRadioRequest -> convert(fromRadioRequest);
            case FromSonosRequest fromSonosRequest -> convert(fromSonosRequest);
        };
    }

    dev.migwel.sts.domain.model.ToRequest convert(ToRequest dto, String username) {
        return switch (dto) {
            case ToDatabaseRequest toDatabaseRequest -> convert(toDatabaseRequest, username);
        };
    }

    dev.migwel.sts.domain.model.FromRadioRequest convert(FromRadioRequest dto) {
        return new dev.migwel.sts.domain.model.FromRadioRequest(dto.getUrl());
    }

    FromRadioRequest convert(dev.migwel.sts.domain.model.FromRadioRequest model) {
        return new FromRadioRequest(model.url());
    }

    dev.migwel.sts.domain.model.FromSonosRequest convert(FromSonosRequest dto) {
        return new dev.migwel.sts.domain.model.FromSonosRequest(
                dto.getHouseholdId(), dto.getGroupId());
    }

    FromSonosRequest convert(dev.migwel.sts.domain.model.FromSonosRequest model) {
        return new FromSonosRequest(model.householdId(), model.groupId());
    }

    dev.migwel.sts.domain.model.ToDatabaseRequest convert(ToDatabaseRequest dto, String username) {
        return new dev.migwel.sts.domain.model.ToDatabaseRequest(username);
    }

    ToDatabaseRequest convert(dev.migwel.sts.domain.model.ToDatabaseRequest model) {
        return new ToDatabaseRequest();
    }
}
