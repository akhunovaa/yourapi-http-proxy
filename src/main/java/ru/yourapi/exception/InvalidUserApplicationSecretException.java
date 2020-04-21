package ru.yourapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class InvalidUserApplicationSecretException extends Exception {

    private final static String ERROR_MESSAGE_ENG = "Invalid X-YourAPI-Key.";
    private final static String ERROR_MESSAGE_RUS = "Ключ X-YourAPI-Key неверен.";

    public InvalidUserApplicationSecretException() {
        super(ERROR_MESSAGE_ENG);
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE_ENG;
    }
}