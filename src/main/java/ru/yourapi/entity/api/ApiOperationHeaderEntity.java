package ru.yourapi.entity.api;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_operation_header")
public class ApiOperationHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "api_operation")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ApiOperationEntity apiOperationEntity;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApiOperationEntity getApiOperationEntity() {
        return apiOperationEntity;
    }

    public void setApiOperationEntity(ApiOperationEntity apiOperationEntity) {
        this.apiOperationEntity = apiOperationEntity;
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
        ApiOperationHeaderEntity that = (ApiOperationHeaderEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(value, that.value) &&
                Objects.equal(isRequired, that.isRequired) &&
                Objects.equal(description, that.description) &&
                Objects.equal(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, value, isRequired, description, note);
    }
}
