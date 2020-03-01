package ru.yourapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yourapi.exception.CustomException;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundResponse extends Response {

    private static final String TEXTAREA_TMP = "<textarea>{success:%b,message:'%s'}</textarea>";

    @JsonProperty
    private final Boolean success;
    @JsonProperty
    private final String message;
    @JsonProperty
    private final Object response;

    public NotFoundResponse() {
        this(true, null, null);
    }

    public NotFoundResponse(CustomException exception) {
        this(false, exception.getMessage(), null);
    }

    public NotFoundResponse(Object response) {
        this(true, null, response);
    }

    public NotFoundResponse(Boolean success, String errorMessage, Object response) {
        this.success= success;
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
