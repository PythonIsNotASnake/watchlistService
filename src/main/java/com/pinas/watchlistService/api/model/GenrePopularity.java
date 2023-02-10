package com.pinas.watchlistService.api.model;

import lombok.Data;

@Data
public class GenrePopularity {
    String genre;
    Long usageInRecords;

    public GenrePopularity(String genre, Long usageInRecords) {
        this.genre = genre;
        this.usageInRecords = usageInRecords;
    }
}
