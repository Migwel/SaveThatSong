package dev.migwel.sts.controller.api;

import dev.migwel.sts.controller.api.dto.FromRadioRequest;
import dev.migwel.sts.controller.api.dto.FromRequest;
import dev.migwel.sts.controller.api.dto.FromSonosRequest;
import dev.migwel.sts.controller.api.dto.ToDatabaseRequest;
import dev.migwel.sts.controller.api.dto.ToRequest;
import dev.migwel.sts.controller.api.dto.ToSpotifyRequest;

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
            case ToSpotifyRequest toSpotifyRequest -> convert(toSpotifyRequest);
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

    dev.migwel.sts.domain.model.ToSpotifyRequest convert(ToSpotifyRequest dto) {
        return new dev.migwel.sts.domain.model.ToSpotifyRequest();
    }

    ToSpotifyRequest convert(dev.migwel.sts.domain.model.ToSpotifyRequest model) {
        return new ToSpotifyRequest();
    }
}
