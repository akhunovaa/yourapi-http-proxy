package ru.yourapi.model;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import ru.yourapi.service.handler.SentCallback;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpRequest implements SentCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    private HttpRequestBase httpRequestBase;
    private HttpServletResponse httpServletResponse;

    public HttpRequest(HttpRequestBase httpRequestBase, HttpServletResponse httpServletResponse) {
        this.httpRequestBase = httpRequestBase;
        this.httpServletResponse = httpServletResponse;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onResult(byte[] response){
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.getWriter().print(new String(response));
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error("Произошла ошибка при попытке записи в HttpServletResponse", e);
        }
    }

    @Override
    public void onError(RuntimeException apiException) {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        LOGGER.error("ERROR: HTTP error. Error message: {}", apiException.getMessage());
    }

    @Override
    public void onException(Exception exception) {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        LOGGER.error("ERROR: Server error. Error message: {}", exception.getMessage());
    }

    @Override
    public HttpRequestBase getHttpRequest() {
        return this.httpRequestBase;
    }
}
