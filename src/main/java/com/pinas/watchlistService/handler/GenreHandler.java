package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.api.model.Genre;
import com.pinas.watchlistService.db.repository.RecordRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreHandler {

    private final RecordRepository repository;

    public GenreHandler(RecordRepository repository) {
        this.repository = repository;
    }

    public List<Genre> getGenres() {
        return repository.findAllByGenreNotNull().stream().collect(Collectors.toList());
    }
}
