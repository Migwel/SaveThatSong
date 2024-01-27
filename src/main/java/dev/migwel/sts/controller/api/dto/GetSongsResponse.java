package dev.migwel.sts.controller.api.dto;

import dev.migwel.sts.controller.api.dto.database.PersistedSong;

import java.util.List;

public record GetSongsResponse(List<PersistedSong> songs) {}
