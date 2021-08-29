package ru.yourapi.controller;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.yourapi.dto.ApiDataDto;
import ru.yourapi.dto.UserPrincipal;
import ru.yourapi.entity.ApiSubscriptionDataEntity;
import ru.yourapi.model.NotFoundResponse;
import ru.yourapi.model.Response;
import ru.yourapi.service.AsyncLoggerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract class AbstractController {

    @Autowired
    AsyncLoggerService asyncLoggerService;

    private final static String X_YOURAPI_VERSION = "YourAPI-0.1";

    public static final String ERROR_MESSAGE = "There was an error";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    protected Response getResponseDto(Object obj) {
        return new Response(true, null, obj);
    }

    protected Response getResponseDtoError(String errorMessage) {
        return new Response(false, errorMessage, null);
    }

    protected NotFoundResponse getNotFoundResponse(String errorMessage) {
        return new NotFoundResponse(false, errorMessage, null);
    }

    public void returnFile(byte[] bytes, String fileName, String charset, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
            httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            httpServletResponse.setContentLength(bytes.length);

            if (charset != null && !charset.isEmpty()) {
                httpServletResponse.setCharacterEncoding(charset);
            }
            httpServletResponse.getOutputStream().write(bytes);
        } catch (Exception ex) {
            LOGGER.error("Download file error", ex);
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void returnString(String str, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE);
            httpServletResponse.getWriter().print(str);
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error("Произошла ошибка при попытке записи в HttpServletResponse", e);
        }
    }

    @SuppressWarnings("deprecation")
    protected void returnJsonString(String str, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.getWriter().print(str);
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error("Произошла ошибка при попытке записи в HttpServletResponse", e);
        }
    }

    @SuppressWarnings("deprecation")
    HttpGet configuredHttpGet(String url, String cookies, ApiDataDto apiDataDto, ApiSubscriptionDataEntity apiSubscriptionDataEntity, UserPrincipal userPrincipal, String xForwardedFor, String xRealIP, String host, String forwardedProto) {
        asyncLoggerService.asyncLogOfCustomMessage("Configuring request GET headers \n{} \n{} \nX-Forwarded-For: {} \nX-Real-IP: {} \nX-Host: {} \nX-Forwarded-Proto: {}", apiDataDto, apiSubscriptionDataEntity, xForwardedFor, xRealIP, host, forwardedProto);
        String requestedUser = userPrincipal.getLogin();
        String subscriptionType = apiSubscriptionDataEntity.getApiSubscriptionTypeEntity().getShortName();
        String proxySecret = apiSubscriptionDataEntity.getApiSubscriptionTypeEntity().getApiDataEntity().getSecretUUID();
        String xRateLimitRequestRemaining = String.valueOf(apiSubscriptionDataEntity.getAvailableBalance() - 1);


        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpGet.setHeader("X-YourAPI-User", requestedUser);
        httpGet.setHeader("X-YourAPI-Subscription", subscriptionType);
        httpGet.setHeader("X-YourAPI-Proxy-Secret", proxySecret);
        httpGet.setHeader("X-YourAPI-Version", X_YOURAPI_VERSION);
        httpGet.setHeader("X-Forwarded-For", xForwardedFor);
        httpGet.setHeader("X-Real-IP", xRealIP);
        httpGet.setHeader("X-Host", host);
        httpGet.setHeader("X-Forwarded-Proto", forwardedProto);
        httpGet.setHeader("X-Request-Remaining-Limit", xRateLimitRequestRemaining);

        if (null != cookies) {
            httpGet.setHeader("Cookie", cookies);
        }
        return httpGet;
    }

    @SuppressWarnings("deprecation")
    HttpPost configuredHttpPost(byte[] body, String url, String cookies, ApiDataDto apiDataDto, ApiSubscriptionDataEntity apiSubscriptionDataEntity, UserPrincipal userPrincipal, String xForwardedFor, String xRealIP, String host, String forwardedProto) {
        asyncLoggerService.asyncLogOfCustomMessage("Configuring request POST headers \n{} \n{} \nX-Forwarded-For: {} \nX-Real-IP: {} \nX-Host: {} \nX-Forwarded-Proto: {}", apiDataDto, apiSubscriptionDataEntity, xForwardedFor, xRealIP, host, forwardedProto);
        String requestedUser = userPrincipal.getLogin();
        String subscriptionType = apiSubscriptionDataEntity.getApiSubscriptionTypeEntity().getShortName();
        String proxySecret = apiSubscriptionDataEntity.getApiSubscriptionTypeEntity().getApiDataEntity().getSecretUUID();
        String xRateLimitRequestRemaining = String.valueOf(apiSubscriptionDataEntity.getAvailableBalance() - 1);

        ByteArrayEntity bae = new ByteArrayEntity(body);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpPost.setHeader("X-YourAPI-User", requestedUser);
        httpPost.setHeader("X-YourAPI-Subscription", subscriptionType);
        httpPost.setHeader("X-YourAPI-Proxy-Secret", proxySecret);
        httpPost.setHeader("X-YourAPI-Version", X_YOURAPI_VERSION);
        httpPost.setHeader("X-Forwarded-For", xForwardedFor);
        httpPost.setHeader("X-Real-IP", xRealIP);
        httpPost.setHeader("X-Host", host);
        httpPost.setHeader("X-Forwarded-Proto", forwardedProto);
        httpPost.setHeader("X-Request-Remaining-Limit", xRateLimitRequestRemaining);


        if (null != cookies) {
            httpPost.setHeader("Cookie", cookies);
        }
        httpPost.setEntity(bae);
        return httpPost;
    }
}
