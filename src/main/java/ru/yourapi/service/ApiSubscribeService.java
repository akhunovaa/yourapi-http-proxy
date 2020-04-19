package ru.yourapi.service;

public interface ApiSubscribeService {

    void subscribeToRequestedApiExists(String userApplicationSecret, String apiShortName);

}
