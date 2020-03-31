package ru.yourapi.entity.api;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_server_data")
public class ApiServerDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "variables")
    private String variables;

    @Column(name = "extensions")
    private String extensions;

    @JoinColumn(name = "api_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private ApiDataEntity apiDataEntity;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public ApiDataEntity getApiDataEntity() {
        return apiDataEntity;
    }

    public void setApiDataEntity(ApiDataEntity apiDataEntity) {
        this.apiDataEntity = apiDataEntity;
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
        ApiServerDataEntity that = (ApiServerDataEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(url, that.url) &&
                Objects.equal(description, that.description) &&
                Objects.equal(variables, that.variables) &&
                Objects.equal(extensions, that.extensions) &&
                Objects.equal(apiDataEntity, that.apiDataEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, url, description, variables, extensions, apiDataEntity);
    }
}
