package ru.yourapi.entity.api;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_operation_type")
public class ApiOperationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "note")
    private String note;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        ApiOperationTypeEntity that = (ApiOperationTypeEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(fullName, that.fullName) &&
                Objects.equal(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, fullName, note);
    }
}
