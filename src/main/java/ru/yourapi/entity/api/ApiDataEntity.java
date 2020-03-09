package ru.yourapi.entity.api;

import ru.yourapi.entity.UserEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    @OneToMany(
            mappedBy = "apiDataEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<ApiPathDataEntity> apiPathDataEntityList;

    @OneToOne(
            mappedBy = "apiDataEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private ApiServerDataEntity apiServerDataEntity;

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

    public List<ApiPathDataEntity> getApiPathDataEntityList() {
        return apiPathDataEntityList;
    }

    public void setApiPathDataEntityList(List<ApiPathDataEntity> apiPathDataEntityList) {
        this.apiPathDataEntityList = apiPathDataEntityList;
    }

    public ApiServerDataEntity getApiServerDataEntity() {
        return apiServerDataEntity;
    }

    public void setApiServerDataEntity(ApiServerDataEntity apiServerDataEntity) {
        this.apiServerDataEntity = apiServerDataEntity;
    }
}
