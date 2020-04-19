package ru.yourapi.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import ru.yourapi.entity.ApiSubscriptionDataEntity;
import ru.yourapi.repository.ApiSubscribeDAO;

import java.util.Optional;

@SuppressWarnings({"deprecation"})
@Repository
public class ApiSubscribeDAOImpl implements ApiSubscribeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Cacheable(value = "subscription-data", key = "#userApplicationSecret + #apiShortName")
    @Override
    public Optional<ApiSubscriptionDataEntity> findAppliedSubscription(String userApplicationSecret, String apiShortName) {
        ApiSubscriptionDataEntity apiSubscriptionDataEntity;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ApiSubscriptionDataEntity.class);
        criteria.createAlias("apiSubscriptionTypeEntity.apiDataEntity", "api");
        criteria.add(Restrictions.eq("banned", false));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.add(Restrictions.eq("userApplicationSecret", userApplicationSecret));
        criteria.add(Restrictions.eq("api.shortName", apiShortName));
        criteria.add(Restrictions.eq("api.isDeleted", Boolean.FALSE));
        criteria.add(Restrictions.gt("availableBalance", 0L));
        criteria.setMaxResults(1);
        apiSubscriptionDataEntity = (ApiSubscriptionDataEntity) criteria.uniqueResult();
        session.close();
        return Optional.ofNullable(apiSubscriptionDataEntity);
    }
}
