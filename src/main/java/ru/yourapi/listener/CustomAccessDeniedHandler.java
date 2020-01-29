package ru.yourapi.listener;

import ru.yourapi.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        final CustomException customException = new CustomException(HttpServletResponse.SC_FORBIDDEN, "Недостаточно прав", "Отсутствуют права на просмотр данного ресурса");
        customException.setPath(request.getServletPath());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        mapper.writeValue(response.getOutputStream(), customException.getBodyExceptionMessage());


    }
}
