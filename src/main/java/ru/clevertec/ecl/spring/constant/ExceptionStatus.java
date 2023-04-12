package ru.clevertec.ecl.spring.constant;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum ExceptionStatus {

    ENTITY_NOT_FOUND(1000,"Lookup entity not found"),
    ENTITY_SQL_EXCEPTION(1001, "Entity have some troubles"),
    ENTITY_DUPLICATE_EXCEPTION(1002,"Entity with field already exist"),
    ENTITY_FIELDS_EXCEPTION(1003,"Entity have trouble with fields"),
    OTHER_REPOSITORY_EXCEPTION(1004, "In repository layer is thrown Exception"),
    METHOD_ARGUMENT_INVALID(1005, "This method not valid that arguments"),
    METHOD_NOT_FOUND(1006, "Current URL or method not found"),
    REQUEST_NOT_VALID(1007, "Current method not valid this http request"),
    DTO_NOT_VALID(1008, "Parameters or arguments not valid"),
    OTHER_WEB_EXCEPTION(1009, "Something going wrong with controllers");
    private final int status;
    private final String message;
}
