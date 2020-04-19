package ru.yourapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import ru.yourapi.dto.*;
import ru.yourapi.entity.api.ApiDataEntity;
import ru.yourapi.entity.api.ApiOperationEntity;
import ru.yourapi.entity.api.ApiOperationParameterEntity;
import ru.yourapi.entity.api.ApiPathDataEntity;
import ru.yourapi.exception.ApiDataNotFoundException;
import ru.yourapi.repository.ApiDAO;
import ru.yourapi.service.ApiDataService;
import ru.yourapi.util.CustomStringUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableAsync
public class ApiDataServiceImpl implements ApiDataService {

    @Autowired
    private ApiDAO apiDAO;

    @Override
    public List<ApiDataDto> getApiDataList(Long userId) {
        return null;
    }

    @Override
    public List<ApiDataDto> apiDataDtoFullList() {
        return null;
    }

    @Override
    public ApiDataDto getApiData(Long id) {
        ApiDataEntity apiDataEntity = apiDAO.findById(id).orElseThrow(() -> new ApiDataNotFoundException("API doesn't exists"));
        return makeApiDataDto(apiDataEntity);
    }

    @Override
    @Cacheable(value = "api-data", key = "#projectName")
    public ApiDataDto getApiData(String projectName) {
        //List<ApiDataEntity> apiDataEntityList = apiDAO.getFullApiList();
        ApiDataEntity apiDataEntity = apiDAO.findByShortName(projectName).orElseThrow(() -> new ApiDataNotFoundException("API doesn't exists"));
        //ApiDataEntity apiDataEntity = apiDataEntityList.parallelStream().filter(apiData -> this.streamFilter(apiData, projectName)).findAny().orElseThrow(() -> new ApiDataNotFoundException("API doesn't exists"));
        return makeApiDataDto(apiDataEntity);
    }

    private ApiDataDto makeApiDataDto(ApiDataEntity apiDataEntity) {
        String shortName = apiDataEntity.getId() + "." + apiDataEntity.getName().trim().replaceAll(" ", "-");
        shortName = shortName.length() > 20 ? shortName.substring(0, 19) : shortName;
        ApiDataDto apiDataDto = new ApiDataDto();
        User user = new User();
        user.setId(apiDataEntity.getUserEntity().getId());
        user.setUsername(apiDataEntity.getUserEntity().getLogin());
        user.setEmail(apiDataEntity.getUserEntity().getEmail());
        apiDataDto.setId(apiDataEntity.getId());
        apiDataDto.setUser(user);
        apiDataDto.setName(shortName);
        apiDataDto.setFullName(apiDataEntity.getName());
        apiDataDto.setApproved(apiDataEntity.getApproved());
        apiDataDto.setBanned(apiDataEntity.getBanned());
        apiDataDto.setPrivate(apiDataEntity.getPrivate());
        apiDataDto.setDescription(apiDataEntity.getDescription());
        apiDataDto.setCategory(apiDataEntity.getApiCategoryEntity().getName());

        List<ApiPathDataDto> apiPathDataDtoList = new ArrayList<>(apiDataEntity.getApiOperationEntities().size());
        for (ApiOperationEntity apiOperationEntity : apiDataEntity.getApiOperationEntities()) {

            List<ApiPathParamDataDto> apiPathParamDataDtoList = new ArrayList<>(apiOperationEntity.getApiOperationParameterEntityList().size());
            ApiPathDataEntity apiPathDataEntity = apiOperationEntity.getApiPathDataEntity();

            for (ApiOperationParameterEntity apiOperationParameterEntity : apiOperationEntity.getApiOperationParameterEntityList()) {
                ApiPathParamDataDto apiPathParamDataDto = ApiPathParamDataDto.newBuilder()
                        .setId(apiOperationParameterEntity.getId())
                        .setInput(apiOperationParameterEntity.getInput())
                        .setName(apiOperationParameterEntity.getName())
                        .setDescription(apiOperationParameterEntity.getDescription())
                        .setRequired(null != apiOperationParameterEntity.getRequired())
                        .setAllowEmptyValued(null != apiOperationParameterEntity.getAllowEmptyValue())
                        .setExample(apiOperationParameterEntity.getExample())
                        .build();
                apiPathParamDataDtoList.add(apiPathParamDataDto);
            }

            ApiPathDataDto apiPathDataDto = ApiPathDataDto.newBuilder()
                    .setId(apiPathDataEntity.getId())
                    .setPath(apiPathDataEntity.getValue())
                    .setDescription(apiPathDataEntity.getDescription())
                    .setSummary(apiPathDataEntity.getSummary())
                    .setType(apiPathDataEntity.getApiOperationTypeEntity().getName())
                    .setApiPathParamDataList(apiPathParamDataDtoList)
                    .build();
            apiPathDataDtoList.add(apiPathDataDto);
        }

        ApiServerDataDto apiServerDataDto = new ApiServerDataDto();
        apiServerDataDto.setUrl(apiDataEntity.getApiServerDataEntity().getUrl());
        apiServerDataDto.setDescription(apiDataEntity.getApiServerDataEntity().getDescription());
        apiDataDto.setApiServerDataDto(apiServerDataDto);
        apiDataDto.setApiPathDataDtoList(apiPathDataDtoList);
        return apiDataDto;
    }

    private boolean streamFilter(ApiDataEntity apiDataEntity, String requestedProjectName) {
        String name = CustomStringUtil.transliterate(apiDataEntity.getName()).trim().replaceAll(" ", "-");
        name = name.length() > 20 ? name.substring(0, 19) : name;
        return requestedProjectName.equalsIgnoreCase(name);
    }

}
