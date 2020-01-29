package ru.yourapi.filter;

import ru.yourapi.provider.JwtTokenProvider;
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
    private JwtTokenProvider jwtTokenProvider;

    @Value("${allow.origin}")
    private String origin = "false";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean allowOrigin = Boolean.valueOf(origin);
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception ex) {
            LOGGER.error("Could not set user authentication in security context", ex);
        }
        if (allowOrigin) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
        }
        chain.doFilter(request, response);
    }

}