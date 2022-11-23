package com.minecount.models.database;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "server_pings")
public class ServerPing extends PanacheEntityBase {
    @EmbeddedId
    public ServerPingKey serverPingKey;

    @Column(name = "player_count", nullable = false)
    public Integer playerCount;

    @Override
    public String toString() {
        return "ServerPing{" +
                "serverPingKey=" + serverPingKey +
                ", playerCount=" + playerCount +
                '}';
    }
}
