package ru.yourapi.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.yourapi.service.AsyncLoggerService;
import ru.yourapi.util.ClientInfoUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdditionalLoggingFilter extends GenericFilterBean {

    @Autowired
    private AsyncLoggerService asyncLoggerService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        String xRealIp = httpServletRequest.getHeader("X-Real-IP");
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        String host = httpServletRequest.getHeader("Host");
        String secret = httpServletRequest.getHeader("x-yourapi-key");
        String xForwardedProto = httpServletRequest.getHeader("X-Forwarded-Proto");
        asyncLoggerService.asyncLogOfIncomingHttpRequest("Request GET from IP: {} OS: {} User-Agent: {} X-Real-IP: {} X-Forwarded-For: {} Host: {} X-Forwarded-Proto: {} x-yourapi-key: {}", clientBrowser, clientOs, clientIp, projectName, xRealIp, xForwardedFor, host, xForwardedProto, secret);
        String headerApply = clientIp + ", " + xForwardedFor + ", " + xRealIp;
        httpServletResponse.setHeader("x-forwarded-for", headerApply);
        httpServletResponse.setHeader("x-real-ip", xRealIp);
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

}