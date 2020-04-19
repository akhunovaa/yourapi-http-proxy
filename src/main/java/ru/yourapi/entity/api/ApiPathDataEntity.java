package ru.yourapi.entity.api;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "api_path_data")
public class  ApiPathDataEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "operation_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ApiOperationEntity apiOperationEntity;

    @JoinColumn(name = "operation_type")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private ApiOperationTypeEntity apiOperationTypeEntity;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
//
    public ApiOperationEntity getApiOperationEntity() {
        return apiOperationEntity;
    }

    public void setApiOperationEntity(ApiOperationEntity apiOperationEntity) {
        this.apiOperationEntity = apiOperationEntity;
    }

    public ApiOperationTypeEntity getApiOperationTypeEntity() {
        return apiOperationTypeEntity;
    }

    public void setApiOperationTypeEntity(ApiOperationTypeEntity apiOperationTypeEntity) {
        this.apiOperationTypeEntity = apiOperationTypeEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiPathDataEntity that = (ApiPathDataEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(value, that.value) &&
                Objects.equal(summary, that.summary) &&
                Objects.equal(description, that.description) &&
                Objects.equal(apiOperationTypeEntity, that.apiOperationTypeEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, value, summary, description, apiOperationTypeEntity);
    }
}
