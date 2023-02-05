package com.pinas.watchlistService.db.repository;

import com.pinas.watchlistService.db.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {
    public Set<Record> findAllByGenreNotNull();
}
