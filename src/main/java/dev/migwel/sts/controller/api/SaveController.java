package dev.migwel.sts.controller.api;

import dev.migwel.sts.controller.api.dto.FromRequest;
import dev.migwel.sts.controller.api.dto.SaveRequest;
import dev.migwel.sts.controller.api.dto.SaveResponse;
import dev.migwel.sts.controller.api.dto.ToRequest;
import dev.migwel.sts.domain.model.SaveResult;
import dev.migwel.sts.domain.service.SaveService;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/save")
public class SaveController {

    private static final Logger logger = LogManager.getLogger(SaveController.class);
    private final Converter converter;
    private final ResultConverter resultConverter;
    private final SaveService saveService;

    @Autowired
    public SaveController(
            Converter converter, ResultConverter resultConverter, SaveService saveService) {
        this.converter = converter;
        this.resultConverter = resultConverter;
        this.saveService = saveService;
    }

    @PostMapping
    <T extends FromRequest, U extends ToRequest> ResponseEntity<SaveResponse> save(
            Authentication authentication, @Valid @RequestBody SaveRequest<T, U> saveRequest) {
        dev.migwel.sts.domain.model.FromRequest fromRequest =
                converter.convert(saveRequest.getFrom());
        dev.migwel.sts.domain.model.ToRequest toRequest =
                converter.convert(saveRequest.getTo(), authentication.getName());
        SaveResult saveResult = saveService.save(fromRequest, toRequest);

        SaveResponse saveResultDto = resultConverter.convert(saveResult);
        if (saveResultDto.song() == null) {
            return new ResponseEntity<>(saveResultDto, HttpStatus.NOT_FOUND);
        }
        if (!saveResultDto.toResult().success()) {
            return new ResponseEntity<>(saveResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(saveResultDto, HttpStatus.OK);
    }
}
