package ru.yourapi.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.yourapi.exception.InvalidUserApplicationSecretException;
import ru.yourapi.exception.UserApplicationNotFoundException;
import ru.yourapi.provider.UserApplicationSecretProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Autowired
    private UserApplicationSecretProvider userApplicationSecretProvider;

    @Autowired
    private HandlerExceptionResolver resolver;

    @Value("${allow.origin}")
    private String origin = "false";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean allowOrigin = Boolean.valueOf(origin);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            String secret = userApplicationSecretProvider.resolveSecret((HttpServletRequest) request);
            if (secret != null && userApplicationSecretProvider.validateUserApplicationSecret(secret)) {
                Authentication auth = userApplicationSecretProvider.getAuthentication(secret);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            if (allowOrigin) {
                HttpServletResponse res = (HttpServletResponse) response;
                res.setHeader("Access-Control-Allow-Origin", "*");
                res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
                res.setHeader("Access-Control-Max-Age", "3600");
                res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
            }
            chain.doFilter(request, response);
        } catch (UserApplicationNotFoundException | InvalidUserApplicationSecretException exception) {
            LOGGER.error("Could not set user authentication in security context", exception);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            resolver.resolveException(httpServletRequest, httpServletResponse, null, exception);
        }
    }

}