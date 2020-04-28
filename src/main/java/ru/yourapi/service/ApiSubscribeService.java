package ru.yourapi.service;

import ru.yourapi.entity.ApiSubscriptionDataEntity;

public interface ApiSubscribeService {

    ApiSubscriptionDataEntity subscribeToRequestedApiExists(String userApplicationSecret, String apiShortName, Long userId);

    void subscribeUseActionSave(ApiSubscriptionDataEntity apiSubscriptionDataEntity);

}
