package com.sensedia.consent.api.domain.model;

import com.sensedia.consent.api.domain.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


class ConsentTest {

    @Test
    @DisplayName("[Scenario 1]: Should create correct consent")
    void Scenario1_should_create_correct_consent() {
        UUID randomId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Consent consent = new Consent();
        consent.setId(randomId);
        consent.setCpf("780.739.860-43");
        consent.setStatus(Status.ACTIVE);
        consent.setCreationDateTime(now);
        consent.setExpirationDateTime(now);
        consent.setAdditionalInfo("consent created");

        assertThat(consent.getId()).isEqualTo(randomId);
        assertThat(consent.getCpf()).isEqualTo("780.739.860-43");
        assertThat(consent.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(consent.getCreationDateTime()).isEqualTo(now);
        assertThat(consent.getExpirationDateTime()).isEqualTo(now);
        assertThat(consent.getAdditionalInfo()).isEqualTo("consent created");
    }

    @Test
    @DisplayName("[Scenario 2]: Shouldn't create consent")
    void Scenario2_shouldnt_create_consent_when_consent_id_is_null() {
        LocalDateTime now = LocalDateTime.now();

        Consent consent = new Consent();
        consent.setId(null);
        consent.setCpf("780.739.860-43");
        consent.setStatus(Status.ACTIVE);
        consent.setCreationDateTime(now);
        consent.setExpirationDateTime(now);
        consent.setAdditionalInfo("consent created");

        assertNull(consent.getId());
        assertThat(consent.getCpf()).isEqualTo("780.739.860-43");
        assertThat(consent.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(consent.getCreationDateTime()).isEqualTo(now);
        assertThat(consent.getExpirationDateTime()).isEqualTo(now);
        assertThat(consent.getAdditionalInfo()).isEqualTo("consent created");
    }


}