package ru.yourapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.sql.Timestamp;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "created",
        "ip"
})
public class ResponsePingDto extends AbstractDto {

    private String ip;
    private Date created;

    public ResponsePingDto(String ip) {
        this.ip = ip;
        this.created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "ResponsePingDto{" +
                "ip='" + ip + '\'' +
                ", created=" + created +
                '}';
    }
}
