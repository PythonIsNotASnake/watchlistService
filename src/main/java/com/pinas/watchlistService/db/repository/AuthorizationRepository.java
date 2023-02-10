package com.pinas.watchlistService.db.repository;

import com.pinas.watchlistService.db.entity.Authorization;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization, String> {
    boolean existsByKeyIn(List<String> keys);
}
