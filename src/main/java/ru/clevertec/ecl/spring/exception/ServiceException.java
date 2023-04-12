package ru.clevertec.ecl.spring.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

}
