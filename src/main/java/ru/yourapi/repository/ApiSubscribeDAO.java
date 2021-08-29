package ru.yourapi.repository;

import ru.yourapi.entity.ApiSubscriptionDataEntity;

import java.util.Optional;

public interface ApiSubscribeDAO {

    Optional<ApiSubscriptionDataEntity> findAppliedSubscription(String userApplicationSecret, String apiShortName, Long userId);

    void subscriptionUpdate(ApiSubscriptionDataEntity apiSubscriptionDataEntity);
}
