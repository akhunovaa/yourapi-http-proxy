package ru.yourapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.yourapi.exception.ErrorSubscribeException;
import ru.yourapi.repository.ApiSubscribeDAO;
import ru.yourapi.service.ApiSubscribeService;

@Service
public class ApiSubscribeServiceImpl implements ApiSubscribeService {

    @Autowired
    private ApiSubscribeDAO apiSubscribeDAO;

    @Override
    public void subscribeToRequestedApiExists(String userApplicationSecret, String apiShortName) {
        if (null == apiShortName || StringUtils.isEmpty(apiShortName)){
            throw new ErrorSubscribeException();
        }
        apiSubscribeDAO.findAppliedSubscription(userApplicationSecret, apiShortName).orElseThrow(ErrorSubscribeException::new);
    }

}
