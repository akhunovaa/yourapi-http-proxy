package ru.yourapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.yourapi.service.AsyncLoggerService;

@Service
public class AsyncLoggerServiceImpl implements AsyncLoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncLoggerServiceImpl.class);

    @Async
    @Override
    public void asyncLogOfIncomingHttpRequest(String message, String... logMessage) {
        LOGGER.info(message, logMessage);
    }

    @Async
    @Override
    public void asyncLogOfCustomMessage(String message, String logMessage) {
        LOGGER.info(message, logMessage);
    }
}
