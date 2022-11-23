package com.minecount.models.database;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Optional;

@Entity
@Table(name = "server_ips")
public class ServerIP extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "server_ips_id_seq", sequenceName = "server_ips_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_ips_id_seq")
    @Column(name = "id", updatable = false, columnDefinition = "serial")
    public Long id;

    @Column(name = "server_id", nullable = false, insertable = false, updatable = false)
    public Long serverId;

    @JoinColumn(name = "server_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Server server;

    @Column(nullable = false)
    public String ip;

    @Column(name = "created_date")
    public Timestamp createdDate;

    @Column(name = "last_modified_date")
    public Timestamp lastModifiedDate;

    public static Optional<ServerIP> findByIP(String ip) {
        return find("ip", ip).firstResultOptional();
    }

    @Override
    public String toString() {
        return "ServerIP{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", ip='" + ip + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
