package com.minecount.models.dtos;

import javax.json.bind.annotation.JsonbDateFormat;
import java.sql.Timestamp;
import java.util.Date;

public class ServerDTO {
    private Long id;
    private String ip;
    private String name;
    private String favicon;
    private String motd;
    private String versionName;
    private Integer versionProtocol;
    private Integer playerCount;
    private Integer maxPlayers;
    private Boolean approved;
    private String notes;

    private String website;
    private String discord;
    private Integer successfulPings;
    private Integer unsuccessfulPings;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date lastPinged;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date createdAt;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date lastModifiedDate;

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public String getFavicon() {
        return favicon;
    }

    public String getMotd() {
        return motd;
    }

    public String getVersionName() {
        return versionName;
    }

    public Integer getVersionProtocol() {
        return versionProtocol;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Boolean getApproved() {
        return approved;
    }

    public String getNotes() {
        return notes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getWebsite() {
        return website;
    }

    public String getDiscord() {
        return discord;
    }

    public Date getLastPinged() {
        return lastPinged;
    }

    public Integer getSuccessfulPings() {
        return successfulPings;
    }

    public Integer getUnsuccessfulPings() {
        return unsuccessfulPings;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionProtocol(Integer versionProtocol) {
        this.versionProtocol = versionProtocol;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public void setLastPinged(Timestamp lastPinged) {
        this.lastPinged = lastPinged;
    }

    public void setSuccessfulPings(Integer successfulPings) {
        this.successfulPings = successfulPings;
    }

    public void setUnsuccessfulPings(Integer unsuccessfulPings) {
        this.unsuccessfulPings = unsuccessfulPings;
    }

    @Override
    public String toString() {
        return "ServerDTO{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", favicon='" + favicon + '\'' +
                ", motd='" + motd + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionProtocol=" + versionProtocol +
                ", playerCount=" + playerCount +
                ", maxPlayers=" + maxPlayers +
                ", approved=" + approved +
                ", notes='" + notes + '\'' +
                ", website='" + website + '\'' +
                ", discord='" + discord + '\'' +
                ", successfulPings=" + successfulPings +
                ", unsuccessfulPings=" + unsuccessfulPings +
                ", lastPinged=" + lastPinged +
                ", createdAt=" + createdAt +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
