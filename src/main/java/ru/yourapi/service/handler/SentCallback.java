package ru.yourapi.service.handler;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

public interface SentCallback {

    void onResult(byte[] response) throws IOException;

    void onError(RuntimeException apiException);

    void onException(Exception exception);

    HttpRequestBase getHttpRequest();
}
