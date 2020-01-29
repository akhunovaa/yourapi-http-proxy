package ru.yourapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.yourapi.dto.ResponsePingDto;
import ru.yourapi.model.Response;
import ru.yourapi.util.ClientInfoUtil;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
@RestController
public class PingController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response proxyServicePing(HttpServletRequest httpServletRequest) {
        String clientBrowser = ClientInfoUtil.getClientBrowser(httpServletRequest);
        String clientOs = ClientInfoUtil.getClientOS(httpServletRequest);
        String clientIp = ClientInfoUtil.getClientIpAddr(httpServletRequest);
        LOGGER.info("Request ping from IP: {} OS: {} User-Agent:", clientIp, clientOs, clientBrowser);
        return getResponseDto(new ResponsePingDto(clientIp));
    }

}
