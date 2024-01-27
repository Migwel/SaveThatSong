package dev.migwel.sts.controller.api;

import dev.migwel.sts.controller.api.dto.GetSongsResponse;
import dev.migwel.sts.controller.api.dto.database.PersistedSong;
import dev.migwel.sts.database.DatabaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/database")
public class DatabaseController {

    private final DatabaseService databaseService;
    private final ResultConverter converter;

    @Autowired
    public DatabaseController(DatabaseService databaseService, ResultConverter converter) {
        this.databaseService = databaseService;
        this.converter = converter;
    }

    @GetMapping("/songs")
    ResponseEntity<GetSongsResponse> getSongs(
            Authentication authentication,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<dev.migwel.sts.domain.model.PersistedSong> songs =
                databaseService.getSongs(
                        authentication.getName(),
                        limit != null ? limit : 50,
                        page != null ? page : 0);
        if (songs.isEmpty()) {
            return new ResponseEntity<>(
                    new GetSongsResponse(Collections.emptyList()), HttpStatus.NO_CONTENT);
        }
        List<PersistedSong> dtoSongs = songs.stream().map(converter::convert).toList();
        var response = new GetSongsResponse(dtoSongs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
