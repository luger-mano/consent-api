package com.sensedia.consent.api.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Consent already exists")
public class ConsentAlreadyExistsException extends RuntimeException {
    public ConsentAlreadyExistsException(String message) {
        super(message);
    }
}
