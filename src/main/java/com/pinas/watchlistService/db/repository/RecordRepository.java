package com.pinas.watchlistService.db.repository;

import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RecordRepository extends JpaRepository<Record, Long> {
    public Set<Genre> findAllByGenreNotNull();
}
