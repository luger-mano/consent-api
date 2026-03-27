package com.sensedia.consent.api.service.idempotency;

import com.sensedia.consent.api.domain.model.IdempotencyKey;
import com.sensedia.consent.api.repository.IdempotencyKeyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdempotencyKeyServiceImpl implements IdempotencyKeyService {

    private final IdempotencyKeyRepository idempotencyKeyRepository;

    @Override
    public Map<String, String> getIdempotencyKey(String key) {
        Optional<IdempotencyKey> idempotencyOpp = idempotencyKeyRepository.findByIdempotencyKey(key);

        return idempotencyOpp.map(idempotencyKey -> Map.of(
                "consentId", idempotencyKey.getConsentId(),
                "message", "Consent request received",
                "status", "processed"
        )).orElseGet(() -> Map.of(
                "consentId", "Null",
                "message", "Consent request received",
                "status", "processed"
        ));
    }

    @Override
    @Transactional
    public IdempotencyKey createIdempotencyKeyByKeyAndConsentId(String key, String consentId) {
        IdempotencyKey idempotencyKey;

        idempotencyKey = new IdempotencyKey();
        idempotencyKey.setIdempotencyKey(key);
        idempotencyKey.setConsentId(consentId);

        return idempotencyKeyRepository.save(idempotencyKey);
    }
}
