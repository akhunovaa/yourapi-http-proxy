package ru.yourapi.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.yourapi.exception.ErrorSubscribeException;
import ru.yourapi.service.ApiSubscribeService;
import ru.yourapi.service.AsyncLoggerService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("deprecation")
@Component
public class SubscriptionCheckFilter extends GenericFilterBean {

    @Autowired
    private AsyncLoggerService asyncLoggerService;

    @Autowired
    private ApiSubscribeService apiSubscribeService;

    @Autowired
    private HandlerExceptionResolver resolver;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String apiShortName = httpServletRequest.getHeader("X-Api-Identifier");
        String userApplicationSecret = httpServletRequest.getHeader("X-YourAPI-key");
        asyncLoggerService.asyncLogOfIncomingHttpRequest("Check subscription of project: {} with user application secret: {}", apiShortName, userApplicationSecret);
        try {
            apiSubscribeService.subscribeToRequestedApiExists(userApplicationSecret, apiShortName);
            chain.doFilter(request, response);
        } catch (ErrorSubscribeException exception) {
            httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
            resolver.resolveException(httpServletRequest, httpServletResponse, null, exception);
        }

    }

}