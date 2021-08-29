package ru.yourapi.repository;

import ru.yourapi.entity.UserEntity;

import java.util.Optional;

public interface UserDao {

    Optional<UserEntity> findByUUID(String uuid);

    Optional<UserEntity> findById(Long id);

}
