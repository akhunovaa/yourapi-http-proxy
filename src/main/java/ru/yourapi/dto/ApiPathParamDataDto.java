package ru.yourapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPathParamDataDto extends AbstractDto {

    private String input;
    private String name;
    private String description;
    private boolean required;
    private boolean allowEmptyValue;
    private String example;

    public class Builder {

        private Builder() {

        }

        public ApiPathParamDataDto.Builder setId(Long id) {
            ApiPathParamDataDto.super.id = id;
            return this;
        }

        public ApiPathParamDataDto.Builder setInput(String input) {
            ApiPathParamDataDto.this.input = input;
            return this;
        }


        public ApiPathParamDataDto.Builder setName(String name) {
            ApiPathParamDataDto.this.name = name;
            return this;
        }

        public ApiPathParamDataDto.Builder setDescription(String description) {
            ApiPathParamDataDto.this.description = description;
            return this;
        }

        public ApiPathParamDataDto.Builder setRequired(boolean required) {
            ApiPathParamDataDto.this.required = required;
            return this;
        }

        public ApiPathParamDataDto.Builder setAllowEmptyValued(boolean allowEmptyValue) {
            ApiPathParamDataDto.this.allowEmptyValue = allowEmptyValue;
            return this;
        }

        public ApiPathParamDataDto.Builder setExample(String example) {
            ApiPathParamDataDto.this.example = example;
            return this;
        }


        public ApiPathParamDataDto build() {
            return ApiPathParamDataDto.this;
        }

    }

    public static ApiPathParamDataDto.Builder newBuilder() {
        return new ApiPathParamDataDto().new Builder();
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isAllowEmptyValue() {
        return allowEmptyValue;
    }

    public void setAllowEmptyValue(boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "ApiPathParamDataDto{" +
                "input='" + input + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", required=" + required +
                ", allowEmptyValue=" + allowEmptyValue +
                ", example='" + example + '\'' +
                ", id=" + id +
                '}';
    }
}
