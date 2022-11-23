package com.minecount.models.database;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "server_pings_1d")
public class ServerPing1D extends PanacheEntityBase {
    @EmbeddedId
    public BucketKey bucketKey;

    @Column(name = "server_id", insertable = false, updatable = false)
    public Integer serverId;

    @Column(insertable = false, updatable = false)
    public Timestamp bucket;
    @Column(name = "avg")
    public Double avg;

    @Column(name = "min")
    public Integer min;

    @Column(name = "max")
    public Integer max;

    public static List<ServerPing1D> fetchByServerId(Integer serverId) {
        return find("serverId", serverId).list();
    }
}
