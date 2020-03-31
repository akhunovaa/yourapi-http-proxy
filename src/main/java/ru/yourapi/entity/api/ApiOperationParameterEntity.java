package ru.yourapi.entity.api;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_operation_parameter")
public class ApiOperationParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "input")
    private String input;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "allow_empty_value")
    private Boolean allowEmptyValue;

    @JoinColumn(name = "api_operation")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ApiOperationEntity apiOperationEntity;

    @Column(name = "example")
    private String example;

    @Column(name = "note")
    private String note;

    @Column(name = "aud_when_create", nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp audWhenCreate;

    @Column(name = "aud_when_update")
    private Timestamp audWhenUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public Boolean getAllowEmptyValue() {
        return allowEmptyValue;
    }

    public void setAllowEmptyValue(Boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
    }

    public ApiOperationEntity getApiOperationEntity() {
        return apiOperationEntity;
    }

    public void setApiOperationEntity(ApiOperationEntity apiOperationEntity) {
        this.apiOperationEntity = apiOperationEntity;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getAudWhenCreate() {
        return audWhenCreate;
    }

    public void setAudWhenCreate(Timestamp audWhenCreate) {
        this.audWhenCreate = audWhenCreate;
    }

    public Timestamp getAudWhenUpdate() {
        return audWhenUpdate;
    }

    public void setAudWhenUpdate(Timestamp audWhenUpdate) {
        this.audWhenUpdate = audWhenUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiOperationParameterEntity that = (ApiOperationParameterEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(input, that.input) &&
                Objects.equal(name, that.name) &&
                Objects.equal(description, that.description) &&
                Objects.equal(isRequired, that.isRequired) &&
                Objects.equal(allowEmptyValue, that.allowEmptyValue) &&
                Objects.equal(example, that.example) &&
                Objects.equal(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, input, name, description, isRequired, allowEmptyValue, example, note);
    }
}
