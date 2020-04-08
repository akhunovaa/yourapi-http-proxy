package ru.yourapi.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yourapi.entity.api.ApiDataEntity;
import ru.yourapi.repository.ApiDAO;

import java.util.List;
import java.util.Optional;

@Repository
public class ApiDAOImpl implements ApiDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<ApiDataEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        ApiDataEntity apiDataEntity = session.get(ApiDataEntity.class, id);
        updateTransaction.commit();
        session.close();
        return Optional.ofNullable(apiDataEntity);
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<ApiDataEntity> getFullApiList() {
        List<ApiDataEntity> apiDataEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ApiDataEntity.class);
        criteria.add(Restrictions.eq("isDeleted", Boolean.FALSE));
        criteria.add(Restrictions.eq("isBanned", Boolean.FALSE));
        apiDataEntityList = criteria.list();
        session.close();
        return apiDataEntityList;
    }
}
