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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import ru.yourapi.dto.ApiDataDto;
import ru.yourapi.dto.ApiPathDataDto;
import ru.yourapi.dto.ApiPathParamDataDto;
import ru.yourapi.dto.UserPrincipal;
import ru.yourapi.entity.ApiSubscriptionDataEntity;
import ru.yourapi.exception.ApiDataNotFoundException;
import ru.yourapi.exception.ApiPathNotFoundException;
import ru.yourapi.exception.EmptyIdException;
import ru.yourapi.service.ApiDataService;
import ru.yourapi.service.ApiSubscribeService;
import ru.yourapi.service.HttpService;
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

    private static final String API_SHORT_NAME_IDENTIFIER_HEADER_NAME = "X-Api-Identifier";
    private static final String USER_APPLICATION_SECRET_KEY_HEADER_NAME = "X-YourAPI-Key";
    private static final String FORWARDED_FOR_HEADER_NAME = "X-Forwarded-For";
    private static final String REAL_IP_HEADER_NAME = "X-Real-IP";
    private static final String HOST_HEADER_NAME = "Host";
    private static final String FORWARDED_PROTO_HEADER_NAME = "X-Forwarded-Proto";


    @Autowired
    private HttpService httpService;

    @Autowired
    private ApiDataService apiDataService;

    @Autowired
    private ApiSubscribeService apiSubscribeService;

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/**", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void proxyServiceSyncGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        Long userId = userPrincipal.getId();
        String apiShortName = resolveApiIdentifier(httpServletRequest);
        String userApplicationSecret = resolveUserApplicationSecretKey(httpServletRequest);
        String xForwardedFor = resolveForwardedForIp(httpServletRequest);
        String xRealIP = resolveRealIp(httpServletRequest);
        String host = resolveHost(httpServletRequest);
        String forwardedProto = resolveProto(httpServletRequest);
        if (null == apiShortName){
            throw new EmptyIdException("API project identifier not found");
        }
        ApiSubscriptionDataEntity apiSubscriptionDataEntity = apiSubscribeService.subscribeToRequestedApiExists(userApplicationSecret, apiShortName, userId);

        ApiDataDto apiDataDto = apiDataService.getApiData(apiShortName);

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

        HttpGet httpGet = configuredHttpGet(fullUrlBuilder.toString(), null, apiDataDto, apiSubscriptionDataEntity, userPrincipal, xForwardedFor, xRealIP, host, forwardedProto);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync GET request to the URL: {}", fullUrlBuilder.toString());
        byte[] response = httpService.sendHttpRequest(httpGet);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync GET request to the URL with response: {}", new String(response, StandardCharsets.UTF_8));
        apiSubscribeService.subscribeUseActionSave(apiSubscriptionDataEntity);
        returnJsonString(new String(response, StandardCharsets.UTF_8), httpServletResponse);
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/**", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void proxyServiceSyncPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        Long userId = userPrincipal.getId();
        String apiShortName = resolveApiIdentifier(httpServletRequest);
        String userApplicationSecret = resolveUserApplicationSecretKey(httpServletRequest);
        String xForwardedFor = resolveForwardedForIp(httpServletRequest);
        String xRealIP = resolveRealIp(httpServletRequest);
        String host = resolveHost(httpServletRequest);
        String forwardedProto = resolveProto(httpServletRequest);
        if (null == apiShortName){
            throw new EmptyIdException("API project identifier not found");
        }
        ApiSubscriptionDataEntity apiSubscriptionDataEntity = apiSubscribeService.subscribeToRequestedApiExists(userApplicationSecret, apiShortName, userId);

        ApiDataDto apiDataDto = apiDataService.getApiData(apiShortName);

        String restOfTheUrl = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] pathParts = restOfTheUrl.split("/");

        Map<String, String[]> params = QueryUtil.getQueryParameters(httpServletRequest);
        int pathIdx = 1;
        StringBuilder restPath = new StringBuilder();

        List<ApiPathDataDto> apiDataDtoApiPathDataDtoList = apiDataDto.getApiPathDataDtoList();
        for (ApiPathDataDto apiPathDataDto : apiDataDtoApiPathDataDtoList) {
            if (apiPathDataDto.getType().equalsIgnoreCase("POST")) {
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
        HttpPost httpPost = configuredHttpPost(byteArray, fullUrlBuilder.toString(), null, apiDataDto, apiSubscriptionDataEntity, userPrincipal, xForwardedFor, xRealIP, host, forwardedProto);
        asyncLoggerService.asyncLogOfCustomMessage("Send sync POST request to the URL: {}", fullUrlBuilder.toString());
        apiSubscribeService.subscribeUseActionSave(apiSubscriptionDataEntity);
        byte[] response = httpService.sendHttpRequest(httpPost);
        returnJsonString(new String(response, StandardCharsets.UTF_8), httpServletResponse);
    }

    private String resolveApiIdentifier(HttpServletRequest request) {
        String apiIdentifierName = request.getHeader(API_SHORT_NAME_IDENTIFIER_HEADER_NAME);
        if (apiIdentifierName != null) {
            return apiIdentifierName;
        }
        return null;
    }

    private String resolveUserApplicationSecretKey(HttpServletRequest request) {
        String apiIdentifierName = request.getHeader(USER_APPLICATION_SECRET_KEY_HEADER_NAME);
        if (apiIdentifierName != null) {
            return apiIdentifierName;
        }
        return null;
    }

    private String resolveForwardedForIp(HttpServletRequest request) {
        String apiIdentifierName = request.getHeader(FORWARDED_FOR_HEADER_NAME);
        if (apiIdentifierName != null) {
            return apiIdentifierName;
        }
        return null;
    }

    private String resolveRealIp(HttpServletRequest request) {
        String apiIdentifierName = request.getHeader(REAL_IP_HEADER_NAME);
        if (apiIdentifierName != null) {
            return apiIdentifierName;
        }
        return null;
    }

    private String resolveHost(HttpServletRequest request) {
        String apiIdentifierName = request.getHeader(HOST_HEADER_NAME);
        if (apiIdentifierName != null) {
            return apiIdentifierName;
        }
        return null;
    }

    private String resolveProto(HttpServletRequest request) {
        String apiIdentifierName = request.getHeader(FORWARDED_PROTO_HEADER_NAME);
        if (apiIdentifierName != null) {
            return apiIdentifierName;
        }
        return null;
    }


}
