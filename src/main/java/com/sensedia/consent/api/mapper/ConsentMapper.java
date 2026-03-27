package com.sensedia.consent.api.mapper;

import com.sensedia.consent.api.domain.model.Consent;
import com.sensedia.consent.api.dto.req.ConsentCreatedRequestDto;
import com.sensedia.consent.api.dto.res.ConsentCreatedResponseDto;
import com.sensedia.consent.api.dto.res.ConsentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsentMapper {

    Consent consentCreatedDtoToConsent(ConsentCreatedRequestDto requestDto);
    ConsentCreatedResponseDto consentToConsentCreatedResponseDto(Consent consent);
    ConsentResponseDto consentToConsentResponseDto(Consent consent);
}
