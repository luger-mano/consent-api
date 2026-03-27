package com.sensedia.consent.api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "idempotencyKey"))
@Getter
@Setter
public class IdempotencyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String idempotencyKey;

    @Column(nullable = false)
    private String consentId;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public IdempotencyKey() {
    }

    public IdempotencyKey(Long id, String idempotencyKey, String consentId, LocalDateTime createdAt, LocalDateTime lockedAt) {
        this.id = id;
        this.idempotencyKey = idempotencyKey;
        this.consentId = consentId;
        this.createdAt = createdAt;
    }
}
