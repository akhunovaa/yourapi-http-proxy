package ru.yourapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.yourapi.model.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
@RestController
public class PingController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response mobileServicePing(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        Map<String, Serializable> headers = Collections.list(httpServletRequest.getHeaderNames()).stream().collect(Collectors.toMap(h -> h, h -> {
            ArrayList<String> headerValues = Collections.list(httpServletRequest.getHeaders(h));
            return headerValues.size() == 1 ? headerValues.get(0) : headerValues;
        }));
        for (String headerName : headers.keySet()) {
            String headerValue = (String) headers.get(headerName);
            LOGGER.info("Header name: {}, Header value: {}", headerName, headerValue);
        }
        return getResponseDto(headers);
    }

}
