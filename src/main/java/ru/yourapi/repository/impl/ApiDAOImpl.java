package ru.yourapi.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yourapi.entity.api.ApiDataEntity;
import ru.yourapi.repository.ApiDAO;

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
}
