package com.musica.dto;

public record SongRecord(
    String title,
    String artist,
    String genre,
    int releaseYear
) {}