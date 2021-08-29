package ru.yourapi.repository;

import ru.yourapi.entity.api.ApiDataEntity;

import java.util.List;
import java.util.Optional;

public interface ApiDAO {

    Optional<ApiDataEntity> findById(Long id);

    Optional<ApiDataEntity> findByShortName(String shortName);

    List<ApiDataEntity> getFullApiList();

}
