package ru.yourapi.controller;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import ru.yourapi.dto.ApiDataDto;
import ru.yourapi.dto.ApiPathDataDto;
import ru.yourapi.dto.ApiPathParamDataDto;
import ru.yourapi.dto.UserPrincipal;
import ru.yourapi.exception.ApiDataNotFoundException;
import ru.yourapi.exception.ApiPathNotFoundException;
import ru.yourapi.model.HttpRequest;
import ru.yourapi.service.ApiDataService;
import ru.yourapi.service.AsyncLoggerService;
import ru.yourapi.service.HttpService;
import ru.yourapi.util.ClientInfoUtil;
import ru.yourapi.util.QueryUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
@RestController
public class ProxyController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    private HttpService httpService;
    @Autowired
    private AsyncLoggerService asyncLoggerService;
    @Autowired
    private ApiDataService apiDataService;

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.OPTIONS}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void proxyServiceSyncGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        Long userId = userPrincipal.getId();
        String userLogin = userPrincipal.getLogin();

        ApiDataDto apiDataDto = apiDataService.getApiData(projectName);

        String restOfTheUrl = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] pathParts = restOfTheUrl.split("/");

        Map<String, String[]> params = QueryUtil.getQueryParameters(httpServletRequest);
        int pathIdx = 1;
        StringBuilder restPath = new StringBuilder();

        List<ApiPathDataDto> apiDataDtoApiPathDataDtoList = apiDataDto.getApiPathDataDtoList();
        for (ApiPathDataDto apiPathDataDto : apiDataDtoApiPathDataDtoList) {
            if (apiPathDataDto.getType().equalsIgnoreCase("GET")) {
                List<ApiPathParamDataDto> apiPathParamDataDtoList = apiPathDataDto.getApiPathParamDataList();
                for (ApiPathParamDataDto apiPathParamDataDto : apiPathParamDataDtoList) {
                    String pathParameterName = apiPathParamDataDto.getName();
                    if (apiPathParamDataDto.getInput().equals("path") && pathParts.length > pathIdx) {
                        params.put(pathParameterName, new String[]{pathParts[pathIdx]});
                        restPath.append("/{").append(apiPathParamDataDto.getName()).append("}");
                        pathIdx++;
                        if (pathParts.length > pathIdx) {
                            restPath.append("/").append(pathParts[pathIdx]);
                        }
                    }
                }
            }
        }

        String[] splittedRestPath = restPath.toString().split("/");
        for (int i = 1; i < splittedRestPath.length; i++) {
            String s = splittedRestPath[i];
            if (!s.startsWith("{") && !s.endsWith("}")) {
                if (pathParts[i].equalsIgnoreCase(s)) {
                    throw new ApiPathNotFoundException();
                }
            }
        }

        if (pathIdx == 1) {
            apiDataDto.getApiPathDataDtoList().parallelStream()
                    .filter(apiPathDataDto -> restOfTheUrl.trim().equalsIgnoreCase(apiPathDataDto.getPath()))
                    .filter(apiPathDataDto -> apiPathDataDto.getType().equalsIgnoreCase("GET"))
                    .findAny().orElseThrow(() -> new ApiDataNotFoundException("API path not found"));
        }

        String serverUrl = apiDataDto.getApiServerDataDto().getUrl();
        StringBuilder fullUrlBuilder = new StringBuilder();
        fullUrlBuilder.append(serverUrl).append(restOfTheUrl).append("?").append(httpServletRequest.getQueryString());

        HttpGet httpGet = configuredHttpGet(fullUrlBuilder.toString(), null);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync GET request to the URL: {}", fullUrlBuilder.toString());
        byte[] response = httpService.sendHttpRequest(httpGet);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync GET request to the URL with response: {}", new String(response, StandardCharsets.UTF_8));
        returnJsonString(new String(response, StandardCharsets.UTF_8), httpServletResponse);
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/**", method = {RequestMethod.POST, RequestMethod.OPTIONS}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void proxyServiceSyncPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        String projectName = httpServletRequest.getHeader("X-Api-Identifier");
        Long userId = userPrincipal.getId();
        String userLogin = userPrincipal.getLogin();

        ApiDataDto apiDataDto = apiDataService.getApiData(projectName);

        String restOfTheUrl = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] pathParts = restOfTheUrl.split("/");

        Map<String, String[]> params = QueryUtil.getQueryParameters(httpServletRequest);
        int pathIdx = 1;
        StringBuilder restPath = new StringBuilder();

        List<ApiPathDataDto> apiDataDtoApiPathDataDtoList = apiDataDto.getApiPathDataDtoList();
        for (ApiPathDataDto apiPathDataDto : apiDataDtoApiPathDataDtoList) {
            if (apiPathDataDto.getType().equalsIgnoreCase("GET")) {
                List<ApiPathParamDataDto> apiPathParamDataDtoList = apiPathDataDto.getApiPathParamDataList();
                for (ApiPathParamDataDto apiPathParamDataDto : apiPathParamDataDtoList) {
                    String pathParameterName = apiPathParamDataDto.getName();
                    if (apiPathParamDataDto.getInput().equals("path") && pathParts.length > pathIdx) {
                        params.put(pathParameterName, new String[]{pathParts[pathIdx]});
                        restPath.append("/{").append(apiPathParamDataDto.getName()).append("}");
                        pathIdx++;
                        if (pathParts.length > pathIdx) {
                            restPath.append("/").append(pathParts[pathIdx]);
                        }
                    }
                }
            }
        }

        String[] splittedRestPath = restPath.toString().split("/");
        for (int i = 1; i < splittedRestPath.length; i++) {
            String s = splittedRestPath[i];
            if (!s.startsWith("{") && !s.endsWith("}")) {
                if (pathParts[i].equalsIgnoreCase(s)) {
                    throw new ApiPathNotFoundException();
                }
            }
        }

        if (pathIdx == 1) {
            apiDataDto.getApiPathDataDtoList().parallelStream()
                    .filter(apiPathDataDto -> restOfTheUrl.trim().equalsIgnoreCase(apiPathDataDto.getPath()))
                    .filter(apiPathDataDto -> apiPathDataDto.getType().equalsIgnoreCase("POST"))
                    .findAny().orElseThrow(() -> new ApiDataNotFoundException("API path not found"));
        }


        String serverUrl = apiDataDto.getApiServerDataDto().getUrl();
        StringBuilder fullUrlBuilder = new StringBuilder();
        fullUrlBuilder.append(serverUrl).append(restOfTheUrl).append("?").append(httpServletRequest.getQueryString());

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = httpServletRequest.getInputStream().read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            LOGGER.error("Body get error", e);
        }
        byte[] byteArray = buffer.toByteArray();
        HttpPost httpPost = configuredHttpPost(byteArray, fullUrlBuilder.toString(), null);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync POST request to the URL: {}", fullUrlBuilder.toString());
        byte[] response = httpService.sendHttpRequest(httpPost);
        returnJsonString(new String(response, StandardCharsets.UTF_8), httpServletResponse);
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
