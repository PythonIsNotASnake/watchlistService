package com.pinas.watchlistService.repository;

import com.pinas.watchlistService.entity.Record;
import com.pinas.watchlistService.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface RecordRepository extends MongoRepository<Record, String> {
    public Set<Genre> findAllByGenreNotNull();
}
