package com.clothing.atum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.BindException;
import java.util.Locale;

@ControllerAdvice // Отлавливает исключения и пытается обработать их
@RequiredArgsConstructor
public class BadRequestControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(org.springframework.validation.BindException exception,
                                                             Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage(
                                "errors.400.creating.product", new Object[0],
                                "400: Error creating product", locale));
        problemDetail.setProperty("errors",
                exception.getAllErrors().stream()
                        .map(MessageSourceResolvable::getDefaultMessage)
                        .toList());
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }
}
