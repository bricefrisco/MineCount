package com.minecount.models.database;

import com.minecount.models.dtos.TotalCountDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "servers")
public class Server extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "servers_id_seq", sequenceName = "servers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servers_id_seq")
    @Column(name = "id", updatable = false, columnDefinition = "serial")
    public Long id;

    @Column(nullable = false)
    public String ip;

    @Column(unique = true)
    public String name;

    @Column(nullable = false)
    public String favicon;

    @Column(nullable = false)
    public String motd;

    @Column(name = "version_name", nullable = false)
    public String versionName;

    @Column(name = "version_protocol", nullable = false)
    public Integer versionProtocol;

    @Column(name = "player_count", nullable = false)
    public Integer playerCount;

    @Column(name = "max_players", nullable = false)
    public Integer maxPlayers;

    @Column
    public Boolean approved;

    @Column
    public String notes;

    @Column
    public String approver;

    @Column
    public String website;

    @Column
    public String discord;

    @Column(name = "successful_pings", nullable = false)
    public Integer successfulPings;

    @Column(name = "unsuccessful_pings", nullable = false)
    public Integer unsuccessfulPings;

    @Column(name = "consecutive_unsuccessful_pings", nullable = false)
    public Integer consecutiveUnsuccessfulPings;

    @Column(name = "last_pinged")
    public Timestamp lastPinged;

    @Column(name = "created_date")
    public Timestamp createdDate;

    @Column(name = "last_modified_date")
    public Timestamp lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "server")
    public List<ServerIP> serverIPs;

    public static PanacheQuery<Server> fetchApproved(Integer pageNumber) {
        return find("approved = true", Sort.by("playerCount", Sort.Direction.Descending))
                .page(pageNumber, 10);
    }

    public static List<Server> fetchAllApproved() {
        return find("approved = true", Sort.by("playerCount", Sort.Direction.Descending)).list();
    }

    public static List<Server> fetchUnapproved() {
        return find("approved IS NULL OR approved = ?1 AND name != 'server-total-counts'", Sort.by("createdDate", Sort.Direction.Ascending), Boolean.FALSE).list();
    }

    public static Optional<Server> fetchByName(String name) {
        return find("approved = TRUE and LOWER(name) = ?1", name.toLowerCase()).firstResultOptional();
    }

    public static Server fetchTotalServer() {
        return find("name = 'server-total-counts'").firstResult();
    }

    public static List<Server> fetchWithoutWebsite() {
        return find("approved = TRUE AND website = null", Sort.by("createdDate", Sort.Direction.Ascending)).list();
    }

    public static List<Server> fetchWithoutDiscord() {
        return find("approved = TRUE AND discord = null", Sort.by("createdDate", Sort.Direction.Ascending)).list();
    }

    public static Optional<Server> dupeSearch(String favicon, String motd) {
        return find("favicon = ?1 or motd = ?2", favicon, motd).firstResultOptional();
    }

    public static TotalCountDTO totalCount() {
        return find("SELECT SUM(playerCount) AS count FROM Server s WHERE s.name != 'server-total-counts'").project(TotalCountDTO.class).firstResult();
    }

    @Override
    public String toString() {
        return "Server{" +
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
                ", approver='" + approver + '\'' +
                ", website='" + website + '\'' +
                ", discord='" + discord + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastPinged=" + lastPinged +
                ", serverIPs=" + serverIPs +
                '}';
    }
}
