package ru.yourapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubscriptionNotFoundException extends RuntimeException {

    private final static String ERROR_MESSAGE_ENG = "API subscription not found";
    private final static String ERROR_MESSAGE_RUS = "Подписка для данного API не найдена";

    public SubscriptionNotFoundException() {
        super(ERROR_MESSAGE_ENG);
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE_ENG;
    }
}