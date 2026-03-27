package com.sensedia.consent.api.service.constant;

import com.sensedia.consent.api.dto.req.ConsentCreatedRequestDto;
import com.sensedia.consent.api.dto.res.ConsentCreatedResponseDto;
import com.sensedia.consent.api.dto.res.ConsentPaginationDto;
import com.sensedia.consent.api.dto.res.ConsentResponseDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ConsentService {

    Map<String, String> createConsent(String idempotencyKey, ConsentCreatedRequestDto requestDto);

    List<ConsentPaginationDto> getAllConsents(int page, int pageSize);

    ConsentResponseDto getById(UUID id);

    ConsentCreatedResponseDto updateConsentById(UUID id, ConsentCreatedRequestDto requestDto);

    String deleteConsentById(UUID id);
}
