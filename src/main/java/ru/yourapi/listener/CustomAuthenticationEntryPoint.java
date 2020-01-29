package ru.yourapi.listener;

import ru.yourapi.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authenticationException) throws IOException, ServletException {
        final CustomException customException = new CustomException(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен", "Для просмотра данного ресурса необходима авторизация");
        customException.setPath(request.getServletPath());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        mapper.writeValue(response.getOutputStream(), customException.getBodyExceptionMessage());
    }
}
