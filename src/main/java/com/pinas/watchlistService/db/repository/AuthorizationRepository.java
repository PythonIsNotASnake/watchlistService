package com.pinas.watchlistService.db.repository;

import com.pinas.watchlistService.db.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization, String> {
}
