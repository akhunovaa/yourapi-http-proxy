package ru.yourapi.entity.api;

import com.google.common.base.Objects;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "api_operation_response")
public class ApiOperationResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "encoding")
    private String encoding;

    @Column(name = "example")
    private String example;

    @JoinColumn(name = "api_operation")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private ApiOperationEntity apiOperationEntity;

    @Column(name = "schema")
    private String schema;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public ApiOperationEntity getApiOperationEntity() {
        return apiOperationEntity;
    }

    public void setApiOperationEntity(ApiOperationEntity apiOperationEntity) {
        this.apiOperationEntity = apiOperationEntity;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiOperationResponseEntity that = (ApiOperationResponseEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(code, that.code) &&
                Objects.equal(description, that.description) &&
                Objects.equal(contentType, that.contentType) &&
                Objects.equal(encoding, that.encoding) &&
                Objects.equal(example, that.example) &&
                Objects.equal(schema, that.schema) &&
                Objects.equal(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, code, description, contentType, encoding, example, schema, note);
    }
}
