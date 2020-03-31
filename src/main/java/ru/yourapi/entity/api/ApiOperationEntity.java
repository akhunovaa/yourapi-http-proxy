package ru.yourapi.entity.api;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "api_operation")
public class ApiOperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "note")
    private String note;

    @JoinColumn(name = "api_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private ApiDataEntity apiDataEntity;

    @Column(name = "aud_when_create", nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp audWhenCreate;

    @Column(name = "aud_when_update")
    private Timestamp audWhenUpdate;

    @OneToOne(mappedBy = "apiOperationEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private ApiPathDataEntity apiPathDataEntity;

    @OneToMany(mappedBy = "apiOperationEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<ApiOperationParameterEntity> apiOperationParameterEntityList;

    @OneToMany(mappedBy = "apiOperationEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<ApiOperationResponseEntity> apiOperationResponseEntityList;

    @OneToMany(mappedBy = "apiOperationEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<ApiOperationHeaderEntity> apiOperationHeaderEntityList;

    public void addApiOperationParameter(ApiOperationParameterEntity apiOperationParameterEntity) {
        apiOperationParameterEntityList.add(apiOperationParameterEntity);
        apiOperationParameterEntity.setApiOperationEntity(this);
    }

    public void removeApiOperationParameter(ApiOperationParameterEntity apiOperationParameterEntity) {
        apiOperationParameterEntityList.remove(apiOperationParameterEntity);
        apiOperationParameterEntity.setApiOperationEntity(null);
    }

    public void addApiOperationResponse(ApiOperationResponseEntity apiOperationResponseEntity) {
        apiOperationResponseEntityList.add(apiOperationResponseEntity);
        apiOperationResponseEntity.setApiOperationEntity(this);
    }

    public void removeApiOperationResponse(ApiOperationResponseEntity apiOperationResponseEntity) {
        apiOperationResponseEntityList.remove(apiOperationResponseEntity);
        apiOperationResponseEntity.setApiOperationEntity(null);
    }


    public void addApiOperationHeader(ApiOperationHeaderEntity apiOperationHeaderEntity) {
        apiOperationHeaderEntityList.add(apiOperationHeaderEntity);
        apiOperationHeaderEntity.setApiOperationEntity(this);
    }

    public void removeApiOperationHeader(ApiOperationHeaderEntity apiOperationHeaderEntity) {
        apiOperationHeaderEntityList.remove(apiOperationHeaderEntity);
        apiOperationHeaderEntity.setApiOperationEntity(null);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
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

    public Set<ApiOperationParameterEntity> getApiOperationParameterEntityList() {
        return apiOperationParameterEntityList;
    }

    public void setApiOperationParameterEntityList(Set<ApiOperationParameterEntity> apiOperationParameterEntityList) {
        this.apiOperationParameterEntityList = apiOperationParameterEntityList;
    }

    public Set<ApiOperationResponseEntity> getApiOperationResponseEntityList() {
        return apiOperationResponseEntityList;
    }

    public void setApiOperationResponseEntityList(Set<ApiOperationResponseEntity> apiOperationResponseEntityList) {
        this.apiOperationResponseEntityList = apiOperationResponseEntityList;
    }

    public Set<ApiOperationHeaderEntity> getApiOperationHeaderEntityList() {
        return apiOperationHeaderEntityList;
    }

    public void setApiOperationHeaderEntityList(Set<ApiOperationHeaderEntity> apiOperationHeaderEntityList) {
        this.apiOperationHeaderEntityList = apiOperationHeaderEntityList;
    }

    public ApiDataEntity getApiDataEntity() {
        return apiDataEntity;
    }

    public void setApiDataEntity(ApiDataEntity apiDataEntity) {
        this.apiDataEntity = apiDataEntity;
    }

    public ApiPathDataEntity getApiPathDataEntity() {
        return apiPathDataEntity;
    }

    public void setApiPathDataEntity(ApiPathDataEntity apiPathDataEntity) {
        this.apiPathDataEntity = apiPathDataEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiOperationEntity that = (ApiOperationEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, summary);
    }
}
