package ru.yourapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.yourapi.entity.ApiSubscriptionDataEntity;
import ru.yourapi.exception.ErrorSubscribeException;
import ru.yourapi.repository.ApiSubscribeDAO;
import ru.yourapi.service.ApiSubscribeService;

@Service
public class ApiSubscribeServiceImpl implements ApiSubscribeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiSubscribeService.class);

    @Autowired
    private ApiSubscribeDAO apiSubscribeDAO;

    @Override
//    @Cacheable(value = "subscription-data", key = "#userApplicationSecret")
    public ApiSubscriptionDataEntity subscribeToRequestedApiExists(String userApplicationSecret, String apiShortName, Long userId) {
        return apiSubscribeDAO.findAppliedSubscription(userApplicationSecret, apiShortName, userId).orElseThrow(ErrorSubscribeException::new);
    }

    @Async
    @Override
    public void subscribeUseActionSave(ApiSubscriptionDataEntity apiSubscriptionDataEntity) {
        long availableBalance = apiSubscriptionDataEntity.getAvailableBalance() - 1;
        apiSubscriptionDataEntity.setAvailableBalance(availableBalance);
        apiSubscribeDAO.subscriptionUpdate(apiSubscriptionDataEntity);
        LOGGER.info("API subscription using state updated to {} \n Subscription: {}", availableBalance, apiSubscriptionDataEntity);
    }
}
