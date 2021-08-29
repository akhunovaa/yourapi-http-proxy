package ru.yourapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.yourapi.exception.ErrorSubscribeException;
import ru.yourapi.exception.InvalidUserApplicationSecretException;
import ru.yourapi.exception.UserApplicationNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@SuppressWarnings("deprecation")
@ControllerAdvice
public class GlobalExceptionHandler extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void handler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOGGER.error("REST Error", ex);
        String err;
        if (ex instanceof MethodArgumentNotValidException) {
            String message = Optional.of(((MethodArgumentNotValidException) ex).getBindingResult().getFieldError().getDefaultMessage()).orElse("Введены неверные данные");
            err = String.format("{\"success\":false,\"message\":\"%s\",\"timestamp\":%s}", message, new Date().getTime());
        } else if (ex instanceof ErrorSubscribeException) {
            String message = "You are not subscribed to this API";
            err = String.format("{\"success\":false,\"message\":\"%s\",\"timestamp\":%s}", message, new Date().getTime());
        } else {
            err = String.format("{\"success\":false,\"message\":\"%s\",\"timestamp\":%s}", ex.getLocalizedMessage(), new Date().getTime());
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().print(err);
        } catch (IOException ex1) {
            LOGGER.error("response.getWriter().print(err) Error {}", err, ex1);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public void handlerAccessDenied(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOGGER.error("REST Error", ex);
        String message;
        String secretKey = request.getHeader("X-YourAPI-Key");
        if (null == secretKey){
            message = "Missing YourAPI application key. Go to https://yourapi.ru to learn how to get your API application key";
        }else {
            message  = "Invalid X-YourAPI-Key";
        }
        String err = String.format("{\"success\":false,\"message\":\"%s\",\"timestamp\":%s}", message, new Date().getTime());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().print(err);
        } catch (IOException ex1) {
            LOGGER.error("response.getWriter().print(err) Error {}", err, ex1);
        }
    }

    @ExceptionHandler(InvalidUserApplicationSecretException.class)
    @ResponseBody
    public void handlerInvalidUserApplicationSecret(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOGGER.error("REST Error", ex);
        String message = ex.getMessage();
        String err = String.format("{\"success\":false,\"message\":\"%s\",\"timestamp\":%s}", message, new Date().getTime());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().print(err);
        } catch (IOException ex1) {
            LOGGER.error("response.getWriter().print(err) Error {}", err, ex1);
        }
    }

    @ExceptionHandler(UserApplicationNotFoundException.class)
    @ResponseBody
    public void handlerNotFound(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOGGER.error("REST Error", ex);
        String message = ex.getMessage();
        String err = String.format("{\"success\":false,\"message\":\"%s\",\"timestamp\":%s}", message, new Date().getTime());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().print(err);
        } catch (IOException ex1) {
            LOGGER.error("response.getWriter().print(err) Error {}", err, ex1);
        }
    }
}