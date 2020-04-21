package ru.yourapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class ApiPathNotFoundException extends RuntimeException {

    private final static String ERROR_MESSAGE_ENG = "API path not found";
    private final static String ERROR_MESSAGE_RUS = "API path not found";

    public ApiPathNotFoundException() {
        super(ERROR_MESSAGE_ENG);
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE_ENG;
    }
}