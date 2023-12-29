package dev.migwel.sts.domain.model;

/**
 * Representation of a song playing "somewhere"
 *
 * @param artist The artist performing that song
 * @param title The title of the number
 * @param rawData It can happen that parsing the artist and title can be tricky and what we have is
 *     raw data of what's playing. We may want this raw data either to save it and parse it manually
 *     later or for debugging reasons
 */
public record Song(String artist, String title, String rawData) {}
