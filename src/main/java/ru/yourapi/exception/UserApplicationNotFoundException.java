package ru.yourapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserApplicationNotFoundException extends Exception {

    private final static String ERROR_MESSAGE_ENG = "Missing YourAPI application key. Go to https://yourapi.ru to learn how to get your API application key";
    private final static String ERROR_MESSAGE_RUS = "Отсутствует ключ для YourAPI приложения. Посетите https://yourapi.ru чтобы узнать подробности";

    public UserApplicationNotFoundException() {
        super(ERROR_MESSAGE_ENG);
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE_ENG;
    }
}