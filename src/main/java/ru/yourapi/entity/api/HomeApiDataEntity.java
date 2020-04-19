package ru.yourapi.entity.api;

import com.google.common.base.Objects;
import ru.yourapi.entity.UserEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_data")
public class HomeApiDataEntity extends ApiData{

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

    @Column(name = "short")
    private String shortName;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "aud_when_create")
    private Timestamp audWhenCreate;

    @Column(name = "aud_when_update")
    private Timestamp audWhenUpdate;

    @Column(name = "uuid")
    private String uuid;

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

    public String getImageToken() {
        return imageToken;
    }

    public void setImageToken(String imageToken) {
        this.imageToken = imageToken;
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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeApiDataEntity that = (HomeApiDataEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(version, that.version) &&
                Objects.equal(description, that.description) &&
                Objects.equal(userEntity, that.userEntity) &&
                Objects.equal(apiCategoryEntity, that.apiCategoryEntity) &&
                Objects.equal(imageToken, that.imageToken) &&
                Objects.equal(shortName, that.shortName) &&
                Objects.equal(isBanned, that.isBanned) &&
                Objects.equal(isApproved, that.isApproved) &&
                Objects.equal(isDeleted, that.isDeleted) &&
                Objects.equal(isPrivate, that.isPrivate) &&
                Objects.equal(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, version, description, userEntity, apiCategoryEntity, imageToken, shortName, isBanned, isApproved, isDeleted, isPrivate, uuid);
    }
}
