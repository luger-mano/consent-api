package com.sensedia.consent.api.service.idempotency;

import com.sensedia.consent.api.domain.model.IdempotencyKey;

import java.util.Map;

public interface IdempotencyKeyService {

    Map<String, String> getIdempotencyKey(String key);

    IdempotencyKey createIdempotencyKeyByKeyAndConsentId(String key, String consentId);
}
