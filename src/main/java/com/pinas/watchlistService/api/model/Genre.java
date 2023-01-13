package com.pinas.watchlistService.api.model;

import lombok.Data;

@Data
public class Genre {
    String genre;

    public Genre(String genre) {
        this.genre = genre;
    }
}
