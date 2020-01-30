package ru.yourapi.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yourapi.service.handler.SentCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ru.yourapi.util.Constants.SOCKET_TIMEOUT;

public abstract class Sender extends HttpSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    private final ExecutorService exe;
    private volatile CloseableHttpClient httpclient;
    private volatile RequestConfig requestConfig;

    protected Sender() {
        super();
        this.exe = Executors.newFixedThreadPool(1);
        //@formatter:off
        httpclient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100)
                .build();

        if (requestConfig == null) {
            requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(SOCKET_TIMEOUT)
                    .setConnectionRequestTimeout(SOCKET_TIMEOUT)
                    .build();
        }
    }

    @Override
    protected final <Callback extends SentCallback> void sendApiMethodAsync(Callback callback) {
        //noinspection Convert2Lambda
        exe.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] responseContent = sendApiMethod(callback.getHttpRequest());
                    try {
                        callback.onResult(responseContent);
                    } catch (RuntimeException e) {
                        callback.onError(e);
                    }
                } catch (IOException e) {
                    callback.onException(e);
                }

            }
        });
    }

    @Override
    protected final byte[] sendApiMethod(HttpRequestBase httpRequest) {
        byte[] responseByte = null;
        try {
            responseByte = sendHttpRequest(httpRequest);
        } catch (IOException e) {
            LOGGER.error("Unable to execute ", e);
        }
        return responseByte;
    }

    private byte[] sendHttpRequest(HttpRequestBase httpRequest) throws IOException {
        httpRequest.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpclient.execute(httpRequest)) {
            HttpEntity ht = response.getEntity();
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode >= 500) {
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                LOGGER.error("ERROR: HTTP server error. Response code {}: {}", responseCode, EntityUtils.toString(buf, StandardCharsets.UTF_8));
                throw new HttpResponseException(responseCode, EntityUtils.toString(buf, StandardCharsets.UTF_8));
            }
            byte[] responseToOut;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ht.writeTo(baos);
                responseToOut = baos.toByteArray();
            }
            return responseToOut;
        }
    }

}
