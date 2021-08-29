package ru.yourapi.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yourapi.entity.UserEntity;
import ru.yourapi.repository.UserDao;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings({"deprecation"})
    public Optional<UserEntity> findByUUID(String uuid) {
        UserEntity user;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("uuid", uuid));
        criteria.setMaxResults(1);
        user = (UserEntity) criteria.uniqueResult();
        session.close();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        UserEntity user = session.get(UserEntity.class, id);
        updateTransaction.commit();
        session.close();
        return Optional.ofNullable(user);
    }
}