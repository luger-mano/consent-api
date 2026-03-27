package com.sensedia.consent.api.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Consent not found")
public class ConsentNotFoundException extends RuntimeException {
    public ConsentNotFoundException(String message) {
        super(message);
    }
}
