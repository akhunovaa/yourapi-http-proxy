package ru.yourapi.controller;

import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import ru.yourapi.dto.ApiDataDto;
import ru.yourapi.exception.ApiDataNotFoundException;
import ru.yourapi.model.HttpRequest;
import ru.yourapi.model.Response;
import ru.yourapi.service.ApiDataService;
import ru.yourapi.service.AsyncLoggerService;
import ru.yourapi.service.HttpService;
import ru.yourapi.util.ClientInfoUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("deprecation")
@RestController
public class ProxyController extends AbstractController {


    @Autowired
    private HttpService httpService;

    @Autowired
    private AsyncLoggerService asyncLoggerService;

    @Autowired
    private ApiDataService apiDataService;

    @RequestMapping(value = "/**", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void proxyServiceSyncGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        String xRealIp = httpServletRequest.getHeader("X-Real-IP");
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        String host = httpServletRequest.getHeader("Host");
        String xForwardedProto = httpServletRequest.getHeader("X-Forwarded-Proto");
        asyncLoggerService.asyncLogOfIncomingHttpRequest("Request GET from IP: {} OS: {} User-Agent: {} X-Real-IP: {} X-Forwarded-For: {} Host: {} X-Forwarded-Proto: {}", clientBrowser, clientOs, clientIp, projectName, xRealIp, xForwardedFor, host, xForwardedProto);
        Long apiDataId = Long.valueOf(projectName.split("-")[0]);
        ApiDataDto apiDataDto = apiDataService.getApiData(apiDataId);
        String restOfTheUrl = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        apiDataDto.getApiPathDataDtoList().parallelStream().filter(apiPathDataDto -> restOfTheUrl.trim().equalsIgnoreCase(apiPathDataDto.getPath())).filter(apiPathDataDto -> apiPathDataDto.getType().equalsIgnoreCase("GET")).findAny().orElseThrow(() -> new ApiDataNotFoundException("API path not found"));
        String serverUrl = apiDataDto.getApiServerDataDto().getUrl();
        String fullUrl = serverUrl + restOfTheUrl;
        if (null != httpServletRequest.getQueryString()) {
            fullUrl = fullUrl + "?" + httpServletRequest.getQueryString();
        }
        HttpGet httpGet = configuredHttpGet(fullUrl, null);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync GET request to the URL: {}", fullUrl);
        byte[] response = httpService.sendHttpRequest(httpGet);
        returnJsonString(new String(response, StandardCharsets.UTF_8), httpServletResponse);
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response proxyServiceSyncPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        String xRealIp = httpServletRequest.getHeader("X-Real-IP");
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        String host = httpServletRequest.getHeader("Host");
        String xForwardedProto = httpServletRequest.getHeader("X-Forwarded-Proto");
        asyncLoggerService.asyncLogOfIncomingHttpRequest("Request GET from IP: {} OS: {} User-Agent: {} X-Real-IP: {} X-Forwarded-For: {} Host: {} X-Forwarded-Proto: {}", clientBrowser, clientOs, clientIp, projectName, xRealIp, xForwardedFor, host, xForwardedProto);
        String restOfTheUrl = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        Long apiDataId = Long.valueOf(projectName.split("-")[0]);
        ApiDataDto apiDataDto = apiDataService.getApiData(apiDataId);
        apiDataDto.getApiPathDataDtoList().parallelStream().filter(apiPathDataDto -> restOfTheUrl.trim().equalsIgnoreCase(apiPathDataDto.getPath())).filter(apiPathDataDto -> apiPathDataDto.getType().equalsIgnoreCase("POST")).findAny().orElseThrow(() -> new ApiDataNotFoundException("API path not found"));
        return getResponseDto(apiDataDto);
    }

    @RequestMapping(value = "/async", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void proxyServiceAsync(@RequestParam(value = "url") String url, @RequestParam(value = "cookies", required = false) String cookies, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        String xRealIp = httpServletRequest.getHeader("X-Real-IP");
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        String host = httpServletRequest.getHeader("Host");
        String xForwardedProto = httpServletRequest.getHeader("X-Forwarded-Proto");
        asyncLoggerService.asyncLogOfIncomingHttpRequest("Request GET from IP: {} OS: {} User-Agent: {} X-Real-IP: {} X-Forwarded-For: {} Host: {} X-Forwarded-Proto: {}", clientBrowser, clientOs, clientIp, projectName, xRealIp, xForwardedFor, host, xForwardedProto);
        HttpGet httpGet = configuredHttpGet(url, cookies);
        HttpRequest httpRequest = new HttpRequest(httpGet, httpServletResponse);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync GET request to the URL: {}", url);
        httpService.sendHttpApiRequestAsync(httpRequest);
    }


}
