package ru.yourapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPathDataDto extends AbstractDto {

    private String path;
    private String summary;
    private String description;
    private String type;
    private List<ApiPathParamDataDto> apiPathParamDataList;

    public class Builder {

        private Builder() {

        }

        public ApiPathDataDto.Builder setId(Long id) {
            ApiPathDataDto.super.id = id;
            return this;
        }

        public ApiPathDataDto.Builder setPath(String path) {
            ApiPathDataDto.this.path = path;
            return this;
        }

        public ApiPathDataDto.Builder setSummary(String summary) {
            ApiPathDataDto.this.summary = summary;
            return this;
        }

        public ApiPathDataDto.Builder setDescription(String description) {
            ApiPathDataDto.this.description = description;
            return this;
        }

        public ApiPathDataDto.Builder setType(String type) {
            ApiPathDataDto.this.type = type;
            return this;
        }

        public ApiPathDataDto.Builder setApiPathParamDataList(List<ApiPathParamDataDto> apiPathParamDataList) {
            ApiPathDataDto.this.apiPathParamDataList = apiPathParamDataList;
            return this;
        }

        public ApiPathDataDto build() {
            return ApiPathDataDto.this;
        }

    }

    public static ApiPathDataDto.Builder newBuilder() {
        return new ApiPathDataDto().new Builder();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ApiPathParamDataDto> getApiPathParamDataList() {
        return apiPathParamDataList;
    }

    public void setApiPathParamDataList(List<ApiPathParamDataDto> apiPathParamDataList) {
        this.apiPathParamDataList = apiPathParamDataList;
    }

    @Override
    public String toString() {
        return "ApiPathDataDto{" +
                "path='" + path + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", apiPathParamDataList=" + apiPathParamDataList +
                ", id=" + id +
                '}';
    }
}
