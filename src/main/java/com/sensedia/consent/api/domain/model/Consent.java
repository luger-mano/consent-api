package com.sensedia.consent.api.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sensedia.consent.api.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_CONSENTS")
@Getter
@Setter
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "consent_id")
    private UUID id;

    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creationDateTime;

    @Column(name = "expiration_date_time")
    private LocalDateTime expirationDateTime;

    @Column(name = "additional_info", length = 50)
    private String additionalInfo;

    public Consent() {
    }

    public Consent(UUID id, String cpf, Status status, LocalDateTime creationDateTime, LocalDateTime expirationDateTime, String additionalInfo) {
        this.id = id;
        this.cpf = cpf;
        this.status = status;
        this.creationDateTime = creationDateTime;
        this.expirationDateTime = expirationDateTime;
        this.additionalInfo = additionalInfo;
    }
}
