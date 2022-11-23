package com.minecount.models.database;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class ServerPingKey implements Serializable {
    public Timestamp time;
    @Column(name = "server_id")
    public Long serverId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerPingKey that = (ServerPingKey) o;
        return Objects.equals(time, that.time) && Objects.equals(serverId, that.serverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, serverId);
    }

    @Override
    public String toString() {
        return "ServerPingKey{" +
                "time=" + time +
                ", serverId=" + serverId +
                '}';
    }
}
