package com.minecount.services;

import com.minecount.models.database.*;
import com.minecount.models.MCPingResponse;
import com.minecount.models.dtos.ServerCountDTO;
import com.minecount.models.dtos.ServerDTO;
import com.minecount.models.dtos.TotalCountDTO;
import com.minecount.models.exceptions.ConflictException;
import com.minecount.models.exceptions.NotFoundException;
import com.minecount.models.rest.ModifyServerRequest;
import com.minecount.models.rest.PaginatedServerResponse;
import com.minecount.models.rest.ServerRequestApproval;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DatabaseService {
    Logger LOGGER = LoggerFactory.getLogger(DatabaseService.class);

    @Transactional
    public PaginatedServerResponse fetchApprovedServers(Integer pageNumber) {
        PanacheQuery<Server> servers = Server.fetchApproved(pageNumber);

        PaginatedServerResponse response = new PaginatedServerResponse();
        response.setCount(servers.count());
        response.setPageCount(servers.pageCount());
        response.setServers(DTOMapper.toServerDTOs(servers.list()));

        return response;
    }

    @Transactional
    public List<Server> fetchAllApprovedServers() {
        return Server.fetchAllApproved();
    }

    @Transactional
    public List<Server> fetchUnapprovedServers() {
        return Server.fetchUnapproved();
    }

    @Transactional
    public Optional<Server> fetchByName(String name) {
        return Server.fetchByName(name);
    }

    @Transactional
    public List<Server> fetchServersWithoutWebsites() {
        return Server.fetchWithoutWebsite();
    }
    @Transactional
    public List<Server> fetchServersWithoutDiscord() {
        return Server.fetchWithoutDiscord();
    }

    @Transactional
    public Server fetchUnapprovedServer(Long id) {
        return Server.findById(id);
    }

    @Transactional
    public TotalCountDTO totalCount() {
        return Server.totalCount();
    }

    @Transactional
    public int writeTotalPing() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Server server = Server.fetchTotalServer();
        TotalCountDTO totalCountDTO = Server.totalCount();

        ServerPingKey serverPingKey = new ServerPingKey();
        serverPingKey.serverId = server.id;
        serverPingKey.time = now;

        ServerPing ping = new ServerPing();
        ping.serverPingKey = serverPingKey;
        ping.playerCount = totalCountDTO.getCount().intValue();

        server.lastPinged = now;
        server.successfulPings += 1;
        server.playerCount = totalCountDTO.getCount().intValue();

        ServerPing.persist(ping);
        Server.persist(server);

        return totalCountDTO.getCount().intValue();
    }

    @Transactional
    public int writePing(Long serverId, MCPingResponse response) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        ServerPingKey serverPingKey = new ServerPingKey();
        serverPingKey.serverId = serverId;
        serverPingKey.time = now;

        ServerPing ping = new ServerPing();
        ping.serverPingKey = serverPingKey;
        ping.playerCount = response.getPlayers().getOnline();
        ServerPing.persist(ping);

        Server server = Server.findById(serverId);
        server.playerCount = response.getPlayers().getOnline();
        server.maxPlayers = response.getPlayers().getMax();
        server.favicon = response.getFavicon();
        server.motd = response.getDescription().getText();
        server.versionName = response.getVersion().getName();
        server.versionProtocol = response.getVersion().getProtocol();
        server.successfulPings = server.successfulPings + 1;
        server.consecutiveUnsuccessfulPings = 0;
        server.lastPinged = now;
        Server.persist(server);

        return response.getPlayers().getOnline();
    }

    @Transactional
    public void writeFailedPing(Long serverId) {
        Server server = Server.findById(serverId);
        server.unsuccessfulPings = server.unsuccessfulPings + 1;
        server.consecutiveUnsuccessfulPings = server.consecutiveUnsuccessfulPings + 1;
        Server.persist(server);
    }

    public ServerDTO dupeCheck(String ip, String favicon, String motd) {
        Optional<ServerIP> existingServerIP = ServerIP.findByIP(ip);
        if (existingServerIP.isPresent()) {
            return DTOMapper.toServerDTO(Server.findById(existingServerIP.get().serverId));
        }

        Optional<Server> existingServer = Server.dupeSearch(favicon, motd);
        return existingServer.map(DTOMapper::toServerDTO).orElse(null);
    }

    @Transactional
    public ServerDTO createServer(String ip, MCPingResponse ping) {
        // Dupe check
        ServerDTO existingServer = dupeCheck(ping.getHostname(), ping.getFavicon(), ping.getDescription().getText());
        if (existingServer != null) {
            throw new ConflictException(String.format("IP, favicon, or motd already associated with server '%s'", existingServer.getId()));
        }

        // Create the server.
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Server server = new Server();
        server.ip = ip;
        server.favicon = ping.getFavicon();
        server.motd = ping.getDescription().getText();
        server.versionName = ping.getVersion().getName();
        server.versionProtocol = ping.getVersion().getProtocol();
        server.playerCount = ping.getPlayers().getOnline();
        server.maxPlayers = ping.getPlayers().getMax();
        server.successfulPings = 0;
        server.unsuccessfulPings = 0;
        server.consecutiveUnsuccessfulPings = 0;
        server.createdDate = now;
        server.lastModifiedDate = now;
        Server.persist(server);

        // Create server IP
        ServerIP serverIP = new ServerIP();
        serverIP.server = server;
        serverIP.ip = ping.getHostname();
        serverIP.createdDate = now;
        serverIP.lastModifiedDate = now;
        ServerIP.persist(serverIP);

        return DTOMapper.toServerDTO(server);
    }

    @Transactional
    public ServerDTO updateServer(ModifyServerRequest request) {
        Optional<Server> maybeServer = Server.findByIdOptional(request.getId());
        if (maybeServer.isEmpty()) {
            throw new NotFoundException("Server not found.");
        }

        Server server = maybeServer.get();

        if (request.getName() != null && !request.getName().isBlank()) {
            server.name = request.getName().trim();
        } else {
            server.name = null;
        }

        if (request.getIp() != null && !request.getIp().isBlank()) {
            server.ip = request.getIp().trim().toLowerCase();
        }

        if (request.getWebsite() != null && !request.getWebsite().isBlank()) {
            server.website = request.getWebsite().trim().toLowerCase();
        } else {
            server.website = null;
        }

        if (request.getDiscord() != null && !request.getDiscord().isBlank()) {
            server.discord = request.getDiscord().trim().toLowerCase();
        } else {
            server.discord = null;
        }

        server.persist();
        return DTOMapper.toServerDTO(server);
    }

    @Transactional
    public ServerDTO serverRequestApproval(ServerRequestApproval request, String userId) {
        Optional<Server> optionalServer = Server.findByIdOptional(request.getId());
        if (optionalServer.isEmpty()) {
            throw new NotFoundException(String.format("Server with id '%s' was not found.", request.getId()));
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Update the server
        Server server = optionalServer.get();
        server.approved = request.getApproved();
        server.name = request.getName().trim();
        server.notes = request.getNotes();
        server.approver = userId;
        server.lastModifiedDate = now;
        Server.persist(server);

        return DTOMapper.toServerDTO(server);
    }

    @Transactional
    public List<ServerCountDTO> fetchBucket10M(Integer serverId) {
        if (serverId == null) {
            serverId = Server.fetchTotalServer().id.intValue();
        }

        return DTOMapper.toServerCountDTOs10M(ServerPing10M.fetchByServerId(serverId));
    }

    @Transactional
    public List<ServerCountDTO> fetchBucket1H(Integer serverId) {
        if (serverId == null) {
            serverId = Server.fetchTotalServer().id.intValue();
        }

        return DTOMapper.toServerCountDTOs1H(ServerPing1H.fetchByServerId(serverId));
    }

    @Transactional
    public List<ServerCountDTO> fetchBucket4H(Integer serverId) {
        if (serverId == null) {
            serverId = Server.fetchTotalServer().id.intValue();
        }

        return DTOMapper.toServerCountDTOs4H(ServerPing4H.fetchByServerId(serverId));
    }

    @Transactional
    public List<ServerCountDTO> fetchBucket1D(Integer serverId) {
        if (serverId == null) {
            serverId = Server.fetchTotalServer().id.intValue();
        }

        return DTOMapper.toServerCountDTOs1D(ServerPing1D.fetchByServerId(serverId));
    }
}
