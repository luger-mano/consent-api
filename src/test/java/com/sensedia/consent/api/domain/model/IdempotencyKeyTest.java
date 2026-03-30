package com.sensedia.consent.api.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class IdempotencyKeyTest {

    @Test
    @DisplayName("[Scenario 1]: Should create correct Idempotency key")
    void Scenario1_should_create_correct_idempotency_key() {
        UUID randomId = UUID.randomUUID();
        String key = "212";
        LocalDateTime now = LocalDateTime.now();

        IdempotencyKey idempotencyKey = new IdempotencyKey();
        idempotencyKey.setId(1L);
        idempotencyKey.setConsentId(String.valueOf(randomId));
        idempotencyKey.setIdempotencyKey(key);
        idempotencyKey.prePersist();
        idempotencyKey.setCreatedAt(now);


        assertThat(idempotencyKey.getId()).isEqualTo(1L);
        assertThat(idempotencyKey.getConsentId()).isEqualTo(String.valueOf(randomId));
        assertThat(idempotencyKey.getIdempotencyKey()).isEqualTo(key);
        assertThat(idempotencyKey.getCreatedAt()).isEqualTo(now);

    }
    @Test
    @DisplayName("[Scenario 2]: Shouldn't create Idempotency key when is null")
    void Scenario2_shouldnt_create_idempotency_key_when_id_is_null() {
        UUID randomId = UUID.randomUUID();
        String key = "212";
        LocalDateTime now = LocalDateTime.now();

        IdempotencyKey idempotencyKey = new IdempotencyKey();
        idempotencyKey.setId(null);
        idempotencyKey.setConsentId(String.valueOf(randomId));
        idempotencyKey.setIdempotencyKey(key);
        idempotencyKey.setCreatedAt(now);


        assertNull(idempotencyKey.getId());
        assertThat(idempotencyKey.getConsentId()).isEqualTo(String.valueOf(randomId));
        assertThat(idempotencyKey.getIdempotencyKey()).isEqualTo(key);
        assertThat(idempotencyKey.getCreatedAt()).isEqualTo(now);

    }


}