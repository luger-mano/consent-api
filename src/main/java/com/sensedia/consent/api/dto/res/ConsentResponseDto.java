package com.sensedia.consent.api.dto.res;

import com.sensedia.consent.api.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ConsentResponseDto {

    private UUID id;
    private String cpf;
    private Status status;
    private LocalDateTime creationDateTime;
    private LocalDateTime expirationDateTime;
    private String additionalInfo;

    public ConsentResponseDto(UUID id, String cpf, Status status, LocalDateTime creationDateTime, LocalDateTime expirationDateTime, String additionalInfo) {
        this.id = id;
        this.cpf = cpf;
        this.status = status;
        this.creationDateTime = creationDateTime;
        this.expirationDateTime = expirationDateTime;
        this.additionalInfo = additionalInfo;
    }

    public ConsentResponseDto() {
    }

}
