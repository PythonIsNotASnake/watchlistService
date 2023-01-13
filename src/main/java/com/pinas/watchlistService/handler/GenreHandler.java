package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.api.model.Genre;
import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.db.repository.RecordRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GenreHandler {

    private final RecordRepository repository;

    public GenreHandler(RecordRepository repository) {
        this.repository = repository;
    }

    public List<Genre> getGenres() {
        Set<Record> recordsWithGenre = repository.findAllByGenreNotNull();
        Set<Genre> genres = recordsWithGenre.stream()
                .map(Record::getGenre)
                .map(genre -> new Genre(genre))
                .collect(Collectors.toSet());

        return genres.stream().collect(Collectors.toList());
    }
}
