package ru.yourapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiSubscribeDto extends AbstractDto {

    @NotNull(message = "Неправильно введен API для подписки")
    @Size(max = 1000, message = "Неправильно введен API для подписки")
    @JsonProperty("api")
    private String apiUUID;

    @Size(max = 1000, message = "Неправильно введен пользователь для подписки")
    @JsonProperty("user")
    private String userUUID;

    @NotNull( message = "Неправильно введен ключ для подписки")
    @Size(min = 1, max = 1000, message = "Неправильно введен ключ для подписки")
    @JsonProperty("api_key")
    private String apiSecretKey;

    @NotNull(message = "Неправильно введен тип для подписки")
    @Size(max = 1000, message = "Неправильно введен тип для подписки")
    @JsonProperty("subscription")
    private String subscriptionType;

    @JsonIgnore
    private Long userId;

    public class Builder {

        private Builder() {

        }

        public ApiSubscribeDto.Builder setId(Long id) {
            ApiSubscribeDto.super.id = id;
            return this;
        }

        public ApiSubscribeDto.Builder setApiUUID(String apiUUID) {
            ApiSubscribeDto.this.apiUUID = apiUUID;
            return this;
        }


        public ApiSubscribeDto.Builder setUserUUID(String userUUID) {
            ApiSubscribeDto.this.userUUID = userUUID;
            return this;
        }

        public ApiSubscribeDto.Builder setApiSecretKey(String apiSecretKey) {
            ApiSubscribeDto.this.apiSecretKey = apiSecretKey;
            return this;
        }

        public ApiSubscribeDto.Builder setSubscriptionType(String subscriptionType) {
            ApiSubscribeDto.this.subscriptionType = subscriptionType;
            return this;
        }

        public ApiSubscribeDto build() {
            return ApiSubscribeDto.this;
        }

    }

    public static ApiSubscribeDto.Builder newBuilder() {
        return new ApiSubscribeDto().new Builder();
    }

    public String getApiUUID() {
        return apiUUID;
    }

    public void setApiUUID(String apiUUID) {
        this.apiUUID = apiUUID;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ApiSubscribeDto{" +
                "apiUUID='" + apiUUID + '\'' +
                ", userUUID='" + userUUID + '\'' +
                ", apiSecretKey='" + apiSecretKey + '\'' +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
