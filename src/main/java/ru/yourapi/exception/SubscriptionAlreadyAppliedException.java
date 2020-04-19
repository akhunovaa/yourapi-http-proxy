package ru.yourapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubscriptionAlreadyAppliedException extends RuntimeException {

    private final static String ERROR_MESSAGE_ENG = "API subscription already applied and not used up";
    private final static String ERROR_MESSAGE_RUS = "Подписка для данного API уже была оформлена и пакет не израсходован";

    public SubscriptionAlreadyAppliedException() {
        super(ERROR_MESSAGE_ENG);
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE_ENG;
    }
}