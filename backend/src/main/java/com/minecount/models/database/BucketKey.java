package com.minecount.models.database;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class BucketKey implements Serializable {
    @Column(name = "server_id")
    public Integer serverId;

    @Column(name = "bucket")
    public Timestamp bucket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BucketKey bucketKey = (BucketKey) o;
        return Objects.equals(serverId, bucketKey.serverId) && Objects.equals(bucket, bucketKey.bucket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, bucket);
    }

    @Override
    public String toString() {
        return "BucketKey{" +
                "serverId=" + serverId +
                ", bucket=" + bucket +
                '}';
    }
}
