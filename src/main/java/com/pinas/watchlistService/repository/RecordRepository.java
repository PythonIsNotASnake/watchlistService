package com.pinas.watchlistService.repository;

import com.pinas.watchlistService.entity.Record;
import com.pinas.watchlistService.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RecordRepository extends JpaRepository<Record, String> {
    public Set<Genre> findAllByGenreNotNull();
}
