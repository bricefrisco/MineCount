package com.minecount.models.dtos;

import java.sql.Timestamp;

public class ServerRequestDTO {
    private Long id;
    private String ip;
    private String favicon;
    private String motd;
    private Integer playerCount;
    private Boolean accepted;
    private Boolean rejected;
    private String notes;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getFavicon() {
        return favicon;
    }

    public String getMotd() {
        return motd;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public String getNotes() {
        return notes;
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

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "ServerRequestDTO{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", favicon='" + favicon + '\'' +
                ", motd='" + motd + '\'' +
                ", playerCount=" + playerCount +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                ", notes='" + notes + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
