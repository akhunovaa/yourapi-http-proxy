package ru.yourapi.service.impl;

import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.stereotype.Service;
import ru.yourapi.service.HttpService;
import ru.yourapi.service.Sender;
import ru.yourapi.service.handler.SentCallback;

@Service
public class HttpServiceImplementation extends Sender implements HttpService{

    @Override
    public <Callback extends SentCallback> void sendHttpApiRequestAsync(Callback callback) {
        executeAsyncRequest(callback);
    }

    @Override
    public <T> T sendHttpRequest(HttpRequestBase httpRequestBase) {
        return executeRequest(httpRequestBase);
    }
}
