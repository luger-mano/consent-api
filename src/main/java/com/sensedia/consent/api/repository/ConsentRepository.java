package com.sensedia.consent.api.repository;

import com.sensedia.consent.api.domain.model.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConsentRepository extends JpaRepository<Consent, UUID> {
    boolean existsByCpf(String cpf);

    Optional<Consent> findByCpf(String cpf);
}
