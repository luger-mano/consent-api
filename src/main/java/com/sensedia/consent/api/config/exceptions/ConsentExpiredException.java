package com.sensedia.consent.api.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED, reason = "Consent is no longer available")
public class ConsentExpiredException extends RuntimeException {
    public ConsentExpiredException(String message) {
        super(message);
    }
}
