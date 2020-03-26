package ru.yourapi.service;

import ru.yourapi.dto.ApiDataDto;

import java.util.List;

public interface ApiDataService {

    List<ApiDataDto> getApiDataList(Long userId);

    List<ApiDataDto> apiDataDtoFullList();

    ApiDataDto getApiData(Long id);

}
