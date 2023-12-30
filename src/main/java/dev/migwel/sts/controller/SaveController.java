package dev.migwel.sts.controller;

import dev.migwel.sts.controller.dto.FromRequest;
import dev.migwel.sts.controller.dto.SaveRequest;
import dev.migwel.sts.controller.dto.ToRequest;
import dev.migwel.sts.domain.service.SaveService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/save")
public class SaveController {

    private static final Logger logger = LogManager.getLogger(SaveController.class);
    private final Converter converter;
    private final SaveService saveService;

    @Autowired
    public SaveController(Converter converter, SaveService saveService) {
        this.converter = converter;
        this.saveService = saveService;
    }

    @PostMapping
    @ResponseBody
    <T extends FromRequest, U extends ToRequest> void save(
            @RequestBody SaveRequest<T, U> saveRequest) {
        dev.migwel.sts.domain.model.FromRequest fromRequest =
                converter.convert(saveRequest.getFrom());
        dev.migwel.sts.domain.model.ToRequest toRequest = converter.convert(saveRequest.getTo());
        saveService.save(fromRequest, toRequest);
    }
}
