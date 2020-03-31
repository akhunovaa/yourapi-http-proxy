package ru.yourapi.entity.api;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_data_info")
public class ApiDataInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "version")
    private String version;

    @Column(name = "description")
    private String description;

    @Column(name = "terms_of_service")
    private String termsOfService;

    @Column(name = "contact")
    private String contact;

    @Column(name = "license")
    private String license;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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
        ApiDataInfoEntity that = (ApiDataInfoEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(title, that.title) &&
                Objects.equal(version, that.version) &&
                Objects.equal(description, that.description) &&
                Objects.equal(termsOfService, that.termsOfService) &&
                Objects.equal(contact, that.contact) &&
                Objects.equal(license, that.license) &&
                Objects.equal(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, version, description, termsOfService, contact, license, extensions);
    }
}
