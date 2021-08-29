package ru.yourapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}