package ru.clevertec.ecl.spring.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.ExceptionDTO;

import java.time.LocalDateTime;

import static ru.clevertec.ecl.spring.constant.ExceptionStatus.METHOD_ARGUMENT_INVALID;
import static ru.clevertec.ecl.spring.constant.ExceptionStatus.METHOD_NOT_FOUND;
import static ru.clevertec.ecl.spring.constant.ExceptionStatus.OTHER_WEB_EXCEPTION;
import static ru.clevertec.ecl.spring.constant.ExceptionStatus.REQUEST_NOT_VALID;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceException.class)
    public ExceptionDTO handleServiceException(ServiceException exception) {
        return ExceptionDTO.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ExceptionDTO handleMethodArgumentException() {
        return ExceptionDTO.builder()
                .message(METHOD_ARGUMENT_INVALID.toString())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ExceptionDTO handleNoHandlerFoundException() {
        return ExceptionDTO.builder()
                .message(METHOD_NOT_FOUND.toString())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionDTO handleMethodNotSupportException() {
        return ExceptionDTO.builder()
                .message(REQUEST_NOT_VALID.toString())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }
    @ExceptionHandler(NoSuchMethodException.class)
    public ExceptionDTO handleNotFoundException() {
        return ExceptionDTO.builder()
                .message(METHOD_NOT_FOUND.toString())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionDTO handleConstraintException() {
        return ExceptionDTO.builder()
                .message(OTHER_WEB_EXCEPTION.toString())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
