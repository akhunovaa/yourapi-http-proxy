package ru.yourapi.controller;

import ru.yourapi.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract class AbstractController {

    public static final String ERROR_MESSAGE = "There was an error";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    protected Response getResponseDto(Object obj) {
        return new Response(true, null, obj);
    }

    protected Response getResponseDtoError(String errorMessage) {
        return new Response(false, errorMessage, null);
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

    protected void returnJsonString(String str, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.getWriter().print(str);
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error("Произошла ошибка при попытке записи в HttpServletResponse", e);
        }

    }
}
