package ru.yourapi.service;

import org.apache.http.client.methods.HttpRequestBase;
import ru.yourapi.service.handler.SentCallback;

public interface HttpService {

    <Callback extends SentCallback> void sendHttpApiRequestAsync(Callback callback);

    <T> T sendHttpRequest(HttpRequestBase httpRequestBase);
}
