package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.api.model.Genre;
import com.pinas.watchlistService.api.model.GenrePopularity;
import com.pinas.watchlistService.api.model.Statistic;
import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.db.repository.RecordRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StatisticHandler {

    private final RecordRepository repository;

    public StatisticHandler(RecordRepository repository) {
        this.repository = repository;
    }

    public Statistic getStatistic() {
        Statistic statistic = new Statistic();
        statistic.setRecordCount(repository.count());

        Set<Record> recordsWithGenre = repository.findAllByGenreNotNull();

        Set<Genre> genres = recordsWithGenre.stream()
                .map(Record::getGenre)
                .map(genre -> new Genre(genre))
                .collect(Collectors.toSet());
        statistic.setGenreCount(Long.valueOf(genres.size()));

        Map<String, Long> genreMap = new HashMap<>();
        for (Record record : recordsWithGenre) {
            if (genreMap.containsKey(record.getGenre())) {
                genreMap.replace(record.getGenre(), genreMap.get(record.getGenre()) + 1L);
            } else {
                genreMap.put(record.getGenre(), 1L);
            }
        }
        statistic.setGenrePopularityList(new ArrayList<>());
        genreMap.entrySet()
                .stream()
                .sorted((f1, f2) -> Long.compare(f2.getValue(), f1.getValue()))
                .forEach(entry -> {
                    if (statistic.getGenrePopularityList().size() < 5) {
                        GenrePopularity popularity = new GenrePopularity(entry.getKey(), entry.getValue());
                        statistic.getGenrePopularityList().add(popularity);
                    }
                });

        return statistic;
    }
}
