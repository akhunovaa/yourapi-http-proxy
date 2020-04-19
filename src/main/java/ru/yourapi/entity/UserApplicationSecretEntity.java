package ru.yourapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "user_application_secret")
public class UserApplicationSecretEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private UserEntity user;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @JsonIgnore
    @Column(name = "plain_value")
    private String plainValue;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "is_banned")
    private boolean isBanned;

    @JsonIgnore
    @Column(name = "aud_when_create")
    private Timestamp audWhenCreate;

    @JsonIgnore
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

    public String getPlainValue() {
        return plainValue;
    }

    public void setPlainValue(String plainValue) {
        this.plainValue = plainValue;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApplicationSecretEntity that = (UserApplicationSecretEntity) o;
        return isDeleted == that.isDeleted &&
                isBanned == that.isBanned &&
                Objects.equal(id, that.id) &&
                Objects.equal(user, that.user) &&
                Objects.equal(name, that.name) &&
                Objects.equal(value, that.value) &&
                Objects.equal(plainValue, that.plainValue) &&
                Objects.equal(audWhenCreate, that.audWhenCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, user, name, value, plainValue, isDeleted, isBanned, audWhenCreate);
    }
}
