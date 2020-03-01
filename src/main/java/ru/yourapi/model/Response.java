package ru.yourapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.yourapi.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private static final String TEXTAREA_TMP = "<textarea>{success:%b,message:'%s'}</textarea>";

    @JsonProperty
    private final Boolean success;
    @JsonProperty
    private final String message;
    @JsonProperty
    private final Object response;

    public Response() {
        this(true, null, null);
    }

    public Response(CustomException exception) {
        this(false, exception.getMessage(), null);
    }

    public Response(Object response) {
        this(true, null, response);
    }

    public Response(Boolean success, String errorMessage, Object response) {
        this.success = success;
        this.message = errorMessage;
        this.response = response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return message;
    }

    public Object getResponse() {
        return response;
    }

    public String wrapTextareaString() {
        return String.format(TEXTAREA_TMP, this.success, this.message == null ? "" : this.message);
    }
}
