package ru.clevertec.ecl.spring.exception;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum ExceptionStatus {
    ENTITY_NOT_FOUND(1000,"Lookup entity not found"),
    ENTITY_SQL_EXCEPTION(1001, "Entity have some troubles: "),
    ENTITY_DUPLICATE_EXCEPTION(1002,"Entity with field already exist"),
    ENTITY_FIELDS_EXCEPTION(1003,"Entity have trouble with fields: "),
    OTHER_REPOSITORY_EXCEPTION(1004, "In repository layer is thrown Exception: ");
    private final int status;
    private final String message;
}
