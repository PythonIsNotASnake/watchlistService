package com.pinas.watchlistService.api.model;

import java.util.List;
import lombok.Data;

@Data
public class Statistic {
    Long recordCount;
    Long genreCount;
    List<GenrePopularity> genrePopularityList;
}
