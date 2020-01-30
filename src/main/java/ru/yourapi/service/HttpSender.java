package ru.yourapi.service;

import org.apache.http.client.methods.HttpRequestBase;
import ru.yourapi.service.handler.SentCallback;

public abstract class HttpSender {

    protected <Callback extends SentCallback> void executeAsyncRequest(Callback callback) throws RuntimeException {
        if (callback == null) {
            throw new RuntimeException("Parameter callback can not be null");
        }
        sendApiMethodAsync(callback);
    }

    protected <T> T executeRequest(HttpRequestBase httpRequest) throws RuntimeException {
        return sendApiMethod(httpRequest);
    }

    abstract <Callback extends SentCallback> void sendApiMethodAsync(Callback callback);

    abstract <T> T sendApiMethod(HttpRequestBase httpRequest);

}
