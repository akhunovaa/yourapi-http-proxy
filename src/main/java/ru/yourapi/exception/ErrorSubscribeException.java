package ru.yourapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ErrorSubscribeException extends RuntimeException {

    private final static String ERROR_MESSAGE_ENG = "You are not subscribed to this API";
    private final static String ERROR_MESSAGE_RUS = "Вы не Подписаны на данное API";

    public ErrorSubscribeException() {
        super(ERROR_MESSAGE_ENG);
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE_ENG;
    }
}