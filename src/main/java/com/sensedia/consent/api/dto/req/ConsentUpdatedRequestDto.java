package com.sensedia.consent.api.dto.req;

import com.sensedia.consent.api.domain.enums.Status;

import java.time.LocalDateTime;

public record ConsentUpdatedRequestDto(
        LocalDateTime expirationDateTime,
        Status status,
        String additionalInfo) {
}