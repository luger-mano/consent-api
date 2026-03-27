package com.sensedia.consent.api.service.idempotency;

import com.sensedia.consent.api.domain.model.IdempotencyKey;
import com.sensedia.consent.api.repository.IdempotencyKeyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdempotencyKeyServiceImplTest {

    @Mock
    private IdempotencyKeyRepository idempotencyKeyRepository;

    @InjectMocks
    private IdempotencyKeyServiceImpl idempotencyKeyService;

    @Test
    @DisplayName("[Scenario 1]: Should return idempotency key")
    void Scenario1_should_return_idempotency_key() {
        String key = "abcd-12345";
        String consentId = String.valueOf(UUID.randomUUID());

        IdempotencyKey idempotencyKey = new IdempotencyKey();
        idempotencyKey.setConsentId(consentId);

        when(idempotencyKeyRepository.findByIdempotencyKey(key))
                .thenReturn(Optional.of(idempotencyKey));

        Map<String, String> result =  idempotencyKeyService.getIdempotencyKey(key);

        assertNotNull(result);
        assertEquals(consentId, result.get("consentId"));
        assertEquals("Consent request received", result.get("message"));
        assertEquals("processed", result.get("status"));

        verify(idempotencyKeyRepository).findByIdempotencyKey(key);
    }

    @Test
    @DisplayName("[Scenario 2]: Should return default value when idempotency key is null")
    void Scenario2_should_return_default_values_when_idempotency_key_is_null() {
        String key = "abcd-12345";
        String consentId = String.valueOf(UUID.randomUUID());

        IdempotencyKey idempotencyKey = new IdempotencyKey();
        idempotencyKey.setConsentId(consentId);

        when(idempotencyKeyRepository.findByIdempotencyKey(key))
                .thenReturn(Optional.empty());

        Map<String, String> result =  idempotencyKeyService.getIdempotencyKey(key);

        assertNotNull(result);
        assertEquals("Null", result.get("consentId"));
        assertEquals("Consent request received", result.get("message"));
        assertEquals("processed", result.get("status"));

        verify(idempotencyKeyRepository).findByIdempotencyKey(key);
    }


    @Test
    @DisplayName("[Scenario 3]: Should create a idempotency key")
    void Scenario3_create_idempotency_key() {
        String key = "abcd-12345";
        String consentId = String.valueOf(UUID.randomUUID());

        IdempotencyKey idempotencyKey = new IdempotencyKey();
        idempotencyKey.setId(1L);
        idempotencyKey.setIdempotencyKey(key);
        idempotencyKey.setCreatedAt(LocalDateTime.now());
        idempotencyKey.setConsentId(consentId);

        when(idempotencyKeyRepository.save(any(IdempotencyKey.class))).thenReturn(idempotencyKey);

        IdempotencyKey idempotencyCreated = idempotencyKeyService.createIdempotencyKeyByKeyAndConsentId(key,
                idempotencyKey.getConsentId());

        assertNotNull(idempotencyCreated);
        assertEquals(1L, idempotencyCreated.getId());
        assertEquals(consentId, idempotencyCreated.getConsentId());
        assertEquals(key, idempotencyCreated.getIdempotencyKey());

        verify(idempotencyKeyRepository).save(any(IdempotencyKey.class));

    }
}