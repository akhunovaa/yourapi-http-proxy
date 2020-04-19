package ru.yourapi.entity;

import com.google.common.base.Objects;
import ru.yourapi.entity.api.ApiDataEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_subscription_type")
public class ApiSubscriptionTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "plain_name")
    private String plainName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "balance")
    private long balance;

    @JoinColumn(name = "api_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private ApiDataEntity apiDataEntity;

    @Column(name = "note")
    private String note;

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

    public String getPlainName() {
        return plainName;
    }

    public void setPlainName(String plainName) {
        this.plainName = plainName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public ApiDataEntity getApiDataEntity() {
        return apiDataEntity;
    }

    public void setApiDataEntity(ApiDataEntity apiDataEntity) {
        this.apiDataEntity = apiDataEntity;
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
        ApiSubscriptionTypeEntity that = (ApiSubscriptionTypeEntity) o;
        return balance == that.balance &&
                Objects.equal(id, that.id) &&
                Objects.equal(plainName, that.plainName) &&
                Objects.equal(shortName, that.shortName) &&
                Objects.equal(fullName, that.fullName) &&
                Objects.equal(apiDataEntity, that.apiDataEntity) &&
                Objects.equal(note, that.note) &&
                Objects.equal(audWhenCreate, that.audWhenCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, plainName, shortName, fullName, balance, apiDataEntity, note, audWhenCreate);
    }
}
