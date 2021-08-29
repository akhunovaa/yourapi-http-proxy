package ru.yourapi.service;

public interface AsyncLoggerService {

    void asyncLogOfIncomingHttpRequest(String message, String... logMessage);

    void asyncLogOfCustomMessage(String message, String logMessage);

    void asyncLogOfCustomMessage(String message, Object... logMessage);

}
