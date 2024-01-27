package dev.migwel.sts.controller.api.dto.database;

import dev.migwel.sts.controller.api.dto.Song;

import java.util.Date;

public record PersistedSong(Song song, Date creationDate) {}
