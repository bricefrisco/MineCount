package com.minecount.models.dtos;

import java.sql.Timestamp;

public class ServerPingDTO {
    private Timestamp time;
    private Long serverId;
    private Integer playerCount;

    public Timestamp getTime() {
        return time;
    }

    public Long getServerId() {
        return serverId;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    @Override
    public String toString() {
        return "ServerPingDTO{" +
                "time=" + time +
                ", serverId=" + serverId +
                ", playerCount=" + playerCount +
                '}';
    }
}
