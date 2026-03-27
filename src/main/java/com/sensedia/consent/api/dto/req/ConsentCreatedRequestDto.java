package com.sensedia.consent.api.dto.req;

import com.sensedia.consent.api.domain.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public record ConsentCreatedRequestDto(
        @CPF
        @NotNull(message = "CPF field must be filled in")
        @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}",
                message = "Cpf field must be ###.###.###-## format") String cpf,
        LocalDateTime creationDateTime,
        LocalDateTime expirationDateTime,
        Status status,
        String additionalInfo) {
}
