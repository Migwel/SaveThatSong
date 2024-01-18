package dev.migwel.sts.controller;

import dev.migwel.sts.sonos.SonosAuthenticationService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sonos")
public class SonosController {

    private final SonosAuthenticationService sonosAuthenticationService;

    public SonosController(SonosAuthenticationService sonosAuthenticationService) {
        this.sonosAuthenticationService = sonosAuthenticationService;
    }

    @GetMapping("/oauthRegister")
    @ResponseBody
    public void oauthRegister(Authentication authentication) {
        sonosAuthenticationService.oauthRegister(authentication.getName());
    }
}
