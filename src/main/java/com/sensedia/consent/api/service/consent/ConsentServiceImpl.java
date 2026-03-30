package com.sensedia.consent.api.service.consent;

import com.sensedia.consent.api.config.exceptions.ConsentNotFoundException;
import com.sensedia.consent.api.domain.enums.Status;
import com.sensedia.consent.api.domain.model.Consent;
import com.sensedia.consent.api.domain.model.IdempotencyKey;
import com.sensedia.consent.api.dto.req.ConsentCreatedRequestDto;
import com.sensedia.consent.api.dto.req.ConsentUpdatedRequestDto;
import com.sensedia.consent.api.dto.res.ConsentCreatedResponseDto;
import com.sensedia.consent.api.dto.res.ConsentPaginationDto;
import com.sensedia.consent.api.dto.res.ConsentResponseDto;
import com.sensedia.consent.api.mapper.ConsentMapper;
import com.sensedia.consent.api.repository.ConsentRepository;
import com.sensedia.consent.api.repository.IdempotencyKeyRepository;
import com.sensedia.consent.api.service.idempotency.IdempotencyKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsentServiceImpl implements ConsentService {

    private final ConsentMapper consentMapper;
    private final ConsentRepository consentRepository;
    private final IdempotencyKeyRepository idempotencyKeyRepository;
    private final IdempotencyKeyService idempotencyKeyService;

    private final Map<String, String> mapHistory = new LinkedHashMap<>();

    @Override
    @Transactional
    public Map<String, String> createConsent(String idempotencyKeyHeader, ConsentCreatedRequestDto requestDto) {

        Optional<Consent> consentOpp = consentRepository.findByCpf(requestDto.cpf());
        if (consentOpp.isPresent()) {
            if (consentOpp.get().getExpirationDateTime().isBefore(LocalDateTime.now())) {
                mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: updateStatusExpiredConsent");
                return updateStatusExpiredConsent(consentOpp.get());
            }
        }

        Optional<IdempotencyKey> idempotencyOpp = idempotencyKeyRepository.findByIdempotencyKey(idempotencyKeyHeader);
        if (idempotencyOpp.isPresent()) {
            return mapResponseReceivedConsent(idempotencyOpp.get().getConsentId());
        }

        Consent consent = consentMapper.consentCreatedDtoToConsent(requestDto);

        consent.setStatus(Status.ACTIVE);
        consent.setCreationDateTime(LocalDateTime.now());
        consent.setExpirationDateTime(LocalDateTime.now().plusMinutes(2));

        Consent consentSaved = consentRepository.save(consent);
        mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: save");
        log.info("Consent saved");

        try{
            idempotencyKeyService.createIdempotencyKeyByKeyAndConsentId(
                    idempotencyKeyHeader, String.valueOf(consentSaved.getId())
            );
            mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: createIdempotencyKeyByKeyAndConsentId");
        } catch (RuntimeException e) {
            mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: getIdempotencyKey");
            idempotencyKeyService.getIdempotencyKey(idempotencyKeyHeader);
        }

        return mapResponseReceivedConsent(String.valueOf(consentSaved.getId()));
    }

    @Override
    public List<ConsentPaginationDto> getAllConsents(int page, int pageSize) {
        Page<Consent> consents = consentRepository.findAll(PageRequest.of(page,
                pageSize));
        mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: getAllConsents");
        return consents.stream()
                .map(consent -> new ConsentPaginationDto(
                        new ConsentResponseDto(
                                consent.getId(),
                                consent.getCpf(),
                                consent.getStatus(),
                                consent.getCreationDateTime(),
                                consent.getExpirationDateTime(),
                                consent.getAdditionalInfo()),
                        page,
                        pageSize,
                        consents.getTotalPages(),
                        consents.getNumberOfElements()))
                .collect(Collectors.toList());
    }

    @Override
    public ConsentResponseDto getConsentById(UUID id) {
        Consent consent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found"));
        mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: getConsentById");

        return consentMapper.consentToConsentResponseDto(consent);
    }

    @Override
    @Transactional
    public ConsentCreatedResponseDto updateConsentById(UUID id, ConsentUpdatedRequestDto requestDto) {
        try {
            Consent consent = consentRepository.findById(id)
                    .orElseThrow(() -> new ConsentNotFoundException("Consent not found"));

            consent.setStatus(requestDto.status());
            consent.setAdditionalInfo(requestDto.additionalInfo());
            consent.setExpirationDateTime(requestDto.expirationDateTime());

            Consent consentUpdated = consentRepository.save(consent);
            mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: updateConsentById");
            log.info("Consent updated");

            return consentMapper.consentToConsentCreatedResponseDto(consentUpdated);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String deleteConsentById(UUID id) {
        Consent consent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found"));

        consent.setStatus(Status.REVOKED);

        consentRepository.save(consent);
        mapHistory.put(String.valueOf(LocalDateTime.now()), "Invoked Method: deleteConsentById");
        log.info("Consent deleted");

        return "Consent deleted";
    }

    @Override
    public Map<String, String> getHistory() {
        return mapHistory;
    }

    public Map<String, String> updateStatusExpiredConsent(Consent consent){
        consent.setStatus(Status.EXPIRED);
        consentRepository.save(consent);

        return Map.of(
                "consentStatus", consent.getStatus().name(),
                "message", "Consent expired",
                "status", "EXPIRED"
        );
    }

    public Map<String, String> mapResponseReceivedConsent(String consentId){
        return Map.of(
                "consentId", consentId,
                "message", "Consent request received",
                "status", "processed"
        );
    }
}
