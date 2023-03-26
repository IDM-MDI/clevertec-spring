package ru.clevertec.ecl.spring.exception;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum ExceptionStatus {
    ENTITY_NOT_FOUND(1000,"Lookup entity not found");
    private final int status;
    private final String message;
}
