package com.sensedia.consent.api.dto.res;

import com.sensedia.consent.api.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ConsentCreatedResponseDto {

    private UUID id;
    private String cpf;
    private Status status;
    private LocalDateTime creationDateTime;
    private LocalDateTime expirationDateTime;
    private String additionalInfo;
}
