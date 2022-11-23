package com.minecount.models.dtos;

import java.sql.Timestamp;

public class ServerIPDTO {
    private Long id;
    private Long serverId;
    private String ip;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;

    public Long getId() {
        return id;
    }

    public Long getServerId() {
        return serverId;
    }

    public String getIp() {
        return ip;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "ServerIPDTO{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", ip='" + ip + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
