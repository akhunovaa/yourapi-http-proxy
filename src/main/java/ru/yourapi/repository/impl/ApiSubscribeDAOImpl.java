package ru.yourapi.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yourapi.entity.ApiSubscriptionDataEntity;
import ru.yourapi.repository.ApiSubscribeDAO;

import java.util.Optional;

@SuppressWarnings({"deprecation"})
@Repository
public class ApiSubscribeDAOImpl implements ApiSubscribeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiSubscribeDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<ApiSubscriptionDataEntity> findAppliedSubscription(String userApplicationSecret, String apiShortName, Long userId) {
        ApiSubscriptionDataEntity apiSubscriptionDataEntity;
        Session session = sessionFactory.openSession();

//        DetachedCriteria userDetachedCriteria = DetachedCriteria.forClass(UserApplicationSecretEntity.class);
//        userDetachedCriteria.createAlias("user", "user");
//        userDetachedCriteria.add(Restrictions.eq("user.id", userId));
//        userDetachedCriteria.setProjection(Projections.property("value"));

        Criteria criteria = session.createCriteria(ApiSubscriptionDataEntity.class);
        criteria.createAlias("apiSubscriptionTypeEntity.apiDataEntity", "api");
        criteria.add(Restrictions.eq("banned", false));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.add(Restrictions.eq("api.shortName", apiShortName));
        criteria.add(Restrictions.eq("api.isDeleted", Boolean.FALSE));
        criteria.add(Restrictions.gt("availableBalance", 0L));

        criteria.add(Restrictions.eq("userApplicationSecret", userApplicationSecret));

//        criteria.add(Subqueries.propertyIn("userApplicationSecret", userDetachedCriteria));

        criteria.setMaxResults(1);
        apiSubscriptionDataEntity = (ApiSubscriptionDataEntity) criteria.uniqueResult();
        session.close();
        return Optional.ofNullable(apiSubscriptionDataEntity);
    }

    @Override
    public void subscriptionUpdate(ApiSubscriptionDataEntity apiSubscriptionDataEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(apiSubscriptionDataEntity);
        session.getTransaction().commit();
        session.close();
    }
}
