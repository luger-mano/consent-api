package com.sensedia.consent.api.config.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;


@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handlerSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        exception.getStackTrace();

        switch (exception) {
            case NullPointerException nullPointerException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
                errorDetail.setProperty("description", "Null object.");

                return errorDetail;
            }
            case DataIntegrityViolationException dataIntegrityViolationException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409), exception.getMessage());
                errorDetail.setProperty("description", "Data breach, duplicate data.");

                return errorDetail;
            }
            case HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(405), exception.getMessage());
                errorDetail.setProperty("description", "Wrong HTTP method.");

                return errorDetail;
            }
            case AccessDeniedException accessDeniedException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "You are not authorized to access this resource.");

                return errorDetail;
            }
            case SignatureException signatureException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("description", "The JWT signature is invalid.");

                return errorDetail;
            }
            case MethodArgumentNotValidException methodArgumentNotValidException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
                errorDetail.setProperty("description", "Input data failed or invalid.");

                return errorDetail;
            }
            case HttpMessageNotReadableException httpMessageNotReadableException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
                errorDetail.setProperty("description", "Request body is inconsistent.");

                return errorDetail;
            }
            case MethodArgumentTypeMismatchException methodArgumentTypeMismatchException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
                errorDetail.setProperty("description", "Invalid argument type.");

                return errorDetail;
            }
            default -> {
            }
        }

        return errorDetail;
    }
}
