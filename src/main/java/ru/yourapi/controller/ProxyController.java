package ru.yourapi.controller;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yourapi.model.HttpRequest;
import ru.yourapi.model.Response;
import ru.yourapi.service.HttpService;
import ru.yourapi.util.ClientInfoUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("deprecation")
@RestController
public class ProxyController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    private HttpService httpService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response proxyServiceSyncGet(@RequestParam(value = "cookies", required = false) String cookies, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        String xRealIp = httpServletRequest.getHeader("X-Real-IP");
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        String host = httpServletRequest.getHeader("Host");
        String xForwardedProto = httpServletRequest.getHeader("X-Forwarded-Proto");
        LOGGER.info("Request GET to project {}:", clientIp, clientOs, clientBrowser, projectName);
        LOGGER.info("Request GET from IP: {} OS: {} User-Agent:", clientIp, clientOs, clientBrowser);
        //HttpGet httpGet = configuredHttpGet(url, cookies);
        //LOGGER.info("Send sync GET request to the URL: {} ", url);
        //byte[] response = httpService.sendHttpRequest(httpGet);
        String responseHeadersName = "X-Api-Identifier: " + projectName + " X-Real-IP: " + xRealIp + " X-Forwarded-For: " + xForwardedFor + " Host: " + host + " X-Forwarded-Proto: " + xForwardedProto;
        httpServletResponse.setStatus(404);
        return getResponseDto(responseHeadersName);
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response proxyServiceSyncPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        String xRealIp = httpServletRequest.getHeader("X-Real-IP");
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        String host = httpServletRequest.getHeader("Host");
        String xForwardedProto = httpServletRequest.getHeader("X-Forwarded-Proto");
        LOGGER.info("Request POST to project {}:", clientIp, clientOs, clientBrowser, projectName);
        LOGGER.info("Request POST from IP: {} OS: {} User-Agent:", clientIp, clientOs, clientBrowser);
        String responseHeadersName = "X-Api-Identifier: " + projectName + " X-Real-IP: " + xRealIp + " X-Forwarded-For: " + xForwardedFor + " Host: " + host + " X-Forwarded-Proto: " + xForwardedProto;
        return getResponseDto(responseHeadersName);
    }

    @RequestMapping(value = "/async", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void proxyServiceAsync(@RequestParam(value = "url")  String url, @RequestParam(value = "cookies", required = false) String cookies, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        LOGGER.info("Request GET from IP: {} OS: {} User-Agent:", clientIp, clientOs, clientBrowser);
        HttpGet httpGet = configuredHttpGet(url, cookies);
        HttpRequest httpRequest = new HttpRequest(httpGet, httpServletResponse);
        LOGGER.info("Send GET request to the URL: {} ", url);
        httpService.sendHttpApiRequestAsync(httpRequest);
    }


}
