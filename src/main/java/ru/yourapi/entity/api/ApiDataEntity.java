package ru.yourapi.entity.api;

import com.google.common.base.Objects;
import ru.yourapi.entity.UserEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "api_data")
public class ApiDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private UserEntity userEntity;

    @JoinColumn(name = "cat_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private ApiCategoryEntity apiCategoryEntity;

    @Column(name = "image_token")
    private String imageToken;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "aud_when_create", nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp audWhenCreate;

    @Column(name = "aud_when_update")
    private Timestamp audWhenUpdate;

    @OneToOne(mappedBy = "apiDataEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private ApiDataInfoEntity apiDataInfoEntity;

    @OneToOne(mappedBy = "apiDataEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private ApiServerDataEntity apiServerDataEntity;

    @OneToMany(mappedBy = "apiDataEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<ApiOperationEntity> apiOperationEntities;

    public void setApiDataInfo(ApiDataInfoEntity apiDataInfoEntity) {
        apiDataInfoEntity.setApiDataEntity(this);
    }

    public void removeApiDataInfo(ApiDataInfoEntity apiDataInfoEntity) {
        apiDataInfoEntity.setApiDataEntity(null);
    }

    public void setApiDataInfo(ApiServerDataEntity apiServerDataEntity) {
        apiServerDataEntity.setApiDataEntity(this);
    }

    public void removeApiDataInfo(ApiServerDataEntity apiServerDataEntity) {
        apiServerDataEntity.setApiDataEntity(null);
    }

//    public void addApiPathData(ApiPathDataEntity apiPathDataEntity) {
//        apiPathDataEntityList.add(apiPathDataEntity);
//        apiPathDataEntity.setApiDataEntity(this);
//    }
//
//    public void removeApiPathData(ApiPathDataEntity apiPathDataEntity) {
//        apiPathDataEntityList.remove(apiPathDataEntity);
//        apiPathDataEntity.setApiDataEntity(null);
//    }

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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ApiCategoryEntity getApiCategoryEntity() {
        return apiCategoryEntity;
    }

    public void setApiCategoryEntity(ApiCategoryEntity apiCategoryEntity) {
        this.apiCategoryEntity = apiCategoryEntity;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public ApiDataInfoEntity getApiDataInfoEntity() {
        return apiDataInfoEntity;
    }

    public void setApiDataInfoEntity(ApiDataInfoEntity apiDataInfoEntity) {
        this.apiDataInfoEntity = apiDataInfoEntity;
    }

    public String getImageToken() {
        return imageToken;
    }

    public void setImageToken(String imageToken) {
        this.imageToken = imageToken;
    }

    public ApiServerDataEntity getApiServerDataEntity() {
        return apiServerDataEntity;
    }

    public void setApiServerDataEntity(ApiServerDataEntity apiServerDataEntity) {
        this.apiServerDataEntity = apiServerDataEntity;
    }

    public Set<ApiOperationEntity> getApiOperationEntities() {
        return apiOperationEntities;
    }

    public void setApiOperationEntities(Set<ApiOperationEntity> apiOperationEntities) {
        this.apiOperationEntities = apiOperationEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiDataEntity that = (ApiDataEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(version, that.version) &&
                Objects.equal(description, that.description) &&
                Objects.equal(userEntity, that.userEntity) &&
                Objects.equal(imageToken, that.imageToken) &&
                Objects.equal(isBanned, that.isBanned) &&
                Objects.equal(isApproved, that.isApproved) &&
                Objects.equal(isDeleted, that.isDeleted) &&
                Objects.equal(isPrivate, that.isPrivate) &&
                Objects.equal(apiDataInfoEntity, that.apiDataInfoEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, version, description, userEntity, imageToken, isBanned, isApproved, isDeleted, isPrivate, apiDataInfoEntity);
    }
}
