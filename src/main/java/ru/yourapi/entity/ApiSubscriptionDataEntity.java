package ru.yourapi.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "api_subscription_data")
public class ApiSubscriptionDataEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "user_application_secret")
    private String userApplicationSecret;

    @Column(name = "available_limit")
    private long availableLimit;

    @Column(name = "available_balance")
    private long availableBalance;

    @JoinColumn(name = "api_subscription_type")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private ApiSubscriptionTypeEntity apiSubscriptionTypeEntity;

    @Column(name = "note")
    private String note;

    @Column(name = "is_banned")
    private boolean banned;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "aud_when_create")
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

    public String getUserApplicationSecret() {
        return userApplicationSecret;
    }

    public void setUserApplicationSecret(String userApplicationSecret) {
        this.userApplicationSecret = userApplicationSecret;
    }

    public long getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(long availableLimit) {
        this.availableLimit = availableLimit;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public ApiSubscriptionTypeEntity getApiSubscriptionTypeEntity() {
        return apiSubscriptionTypeEntity;
    }

    public void setApiSubscriptionTypeEntity(ApiSubscriptionTypeEntity apiSubscriptionTypeEntity) {
        this.apiSubscriptionTypeEntity = apiSubscriptionTypeEntity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
        ApiSubscriptionDataEntity that = (ApiSubscriptionDataEntity) o;
        return availableLimit == that.availableLimit &&
                availableBalance == that.availableBalance &&
                banned == that.banned &&
                deleted == that.deleted &&
                Objects.equal(id, that.id) &&
                Objects.equal(value, that.value) &&
                Objects.equal(userApplicationSecret, that.userApplicationSecret) &&
                Objects.equal(apiSubscriptionTypeEntity, that.apiSubscriptionTypeEntity) &&
                Objects.equal(note, that.note) &&
                Objects.equal(audWhenCreate, that.audWhenCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, value, userApplicationSecret, availableLimit, availableBalance, apiSubscriptionTypeEntity, note, banned, deleted, audWhenCreate);
    }
}
