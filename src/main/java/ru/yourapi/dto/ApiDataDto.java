package ru.yourapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiDataDto extends AbstractDto {

    private String name;
    private String fullName;
    private String description;
    private String category;
    private String version;
    private String url;
    private String note;

    @JsonProperty("username")
    private User user;
    private Boolean banned;
    private Boolean approved;
    private Boolean isPrivate;
    private Timestamp created;
    private Timestamp updated;

    @JsonProperty("paths")
    private List<ApiPathDataDto> apiPathDataDtoList;

    @JsonProperty("server")
    private ApiServerDataDto apiServerDataDto;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<ApiPathDataDto> getApiPathDataDtoList() {
        return apiPathDataDtoList;
    }

    public void setApiPathDataDtoList(List<ApiPathDataDto> apiPathDataDtoList) {
        this.apiPathDataDtoList = apiPathDataDtoList;
    }

    public ApiServerDataDto getApiServerDataDto() {
        return apiServerDataDto;
    }

    public void setApiServerDataDto(ApiServerDataDto apiServerDataDto) {
        this.apiServerDataDto = apiServerDataDto;
    }

    @Override
    public String toString() {
        return "ApiDataDto{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", note='" + note + '\'' +
                ", user=" + user +
                ", banned=" + banned +
                ", approved=" + approved +
                ", isPrivate=" + isPrivate +
                ", created=" + created +
                ", updated=" + updated +
                ", apiPathDataDtoList=" + apiPathDataDtoList +
                ", apiServerDataDto=" + apiServerDataDto +
                '}';
    }
}
