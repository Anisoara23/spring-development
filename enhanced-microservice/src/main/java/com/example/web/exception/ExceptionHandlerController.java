package com.example.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            HttpServletRequest request,
            InsufficientAuthenticationException ex
    ) {
        LOGGER.error(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                FORBIDDEN,
                LocalDateTime.now(),
                request.getServletPath()
        );
        return new ResponseEntity<>(errorResponse, FORBIDDEN);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(
            HttpServletRequest request,
            NoSuchElementException ex
    ) {
        LOGGER.error(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                NOT_FOUND,
                LocalDateTime.now(),
                request.getServletPath()
        );
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpServerErrorException(
            HttpServletRequest request,
            HttpServerErrorException ex
    ) {
        LOGGER.error(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                CONFLICT,
                LocalDateTime.now(),
                request.getServletPath()
        );

        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleJdbcSQLIntegrityConstraintViolationException(
            HttpServletRequest request,
            JdbcSQLIntegrityConstraintViolationException ex
    ) {
        LOGGER.error(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                CONFLICT,
                LocalDateTime.now(),
                request.getServletPath()
        );

        return new ResponseEntity<>(errorResponse, CONFLICT);
    }
}

