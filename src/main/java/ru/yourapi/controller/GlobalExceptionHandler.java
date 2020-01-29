package ru.yourapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @SuppressWarnings("deprecation")
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void handler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOGGER.error("REST Error", ex);
        String err = String.format("{\"success\":false,\"message\":\"%s\",\"timestamp\":%s}", ex.getLocalizedMessage(), new Date().getTime());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().print(err);
        } catch (IOException ex1) {
            LOGGER.error("response.getWriter().print(err) Error {}", err, ex1);
        }
    }
}