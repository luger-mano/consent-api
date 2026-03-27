package com.sensedia.consent.api.repository;

import com.sensedia.consent.api.domain.model.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey, String> {
    Optional<IdempotencyKey> findByIdempotencyKey(String key);
}
