package dev.migwel.sts.controller;

import dev.migwel.sts.spotify.SpotifyAuthenticationService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spotify")
public class SpotifyController {

    private final SpotifyAuthenticationService spotifyAuthenticationService;

    public SpotifyController(SpotifyAuthenticationService spotifyAuthenticationService) {
        this.spotifyAuthenticationService = spotifyAuthenticationService;
    }

    @GetMapping("/oauthRegister")
    @ResponseBody
    void oauthRegister(Authentication authentication) {
        spotifyAuthenticationService.oauthRegister(authentication.getName());
    }
}
