package ru.yourapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yourapi.entity.ApiSubscriptionDataEntity;
import ru.yourapi.exception.ErrorSubscribeException;
import ru.yourapi.repository.ApiSubscribeDAO;
import ru.yourapi.service.ApiSubscribeService;

@Service
public class ApiSubscribeServiceImpl implements ApiSubscribeService {

    @Autowired
    private ApiSubscribeDAO apiSubscribeDAO;

    @Override
    public ApiSubscriptionDataEntity subscribeToRequestedApiExists(String userApplicationSecret, String apiShortName, Long userId) {
        return apiSubscribeDAO.findAppliedSubscription(userApplicationSecret, apiShortName, userId).orElseThrow(ErrorSubscribeException::new);
    }

}
