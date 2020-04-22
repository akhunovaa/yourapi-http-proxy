package ru.yourapi.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.yourapi.provider.UserApplicationSecretProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilterProxyFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilterProxyFilter.class);

    @Autowired
    private UserApplicationSecretProvider userApplicationSecretProvider;

    @Autowired
    private HandlerExceptionResolver resolver;

    @Value("${allow.origin}")
    private String origin = "false";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

}