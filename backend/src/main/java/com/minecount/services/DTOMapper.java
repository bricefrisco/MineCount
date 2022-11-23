package com.minecount.services;

import com.minecount.models.database.*;
import com.minecount.models.dtos.ServerCountDTO;
import com.minecount.models.dtos.ServerDTO;
import com.minecount.models.dtos.ServerIPDTO;
import com.minecount.models.dtos.ServerPingDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {
    public static List<ServerDTO> toServerDTOs(List<Server> servers) {
        return servers.stream().map(DTOMapper::toServerDTO).collect(Collectors.toList());
    }

    public static ServerDTO toServerDTO(Server server) {
        ServerDTO dto = new ServerDTO();
        dto.setId(server.id);
        dto.setIp(server.ip);
        dto.setName(server.name);
        dto.setFavicon(server.favicon);
        dto.setMotd(server.motd);
        dto.setVersionName(server.versionName);
        dto.setVersionProtocol(server.versionProtocol);
        dto.setPlayerCount(server.playerCount);
        dto.setMaxPlayers(server.maxPlayers);
        dto.setApproved(server.approved);
        dto.setNotes(server.notes);
        dto.setWebsite(server.website);
        dto.setDiscord(server.discord);
        dto.setSuccessfulPings(server.successfulPings);
        dto.setUnsuccessfulPings(server.unsuccessfulPings);
        dto.setLastPinged(server.lastPinged);
        dto.setCreatedAt(server.createdDate);
        dto.setLastModifiedDate(server.lastModifiedDate);
        return dto;
    }

    public static List<ServerIPDTO> toServerIPDTOs(List<ServerIP> serverIPS) {
        return serverIPS.stream().map(DTOMapper::toServerIPDTO).collect(Collectors.toList());
    }

    public static ServerIPDTO toServerIPDTO(ServerIP serverIP) {
        ServerIPDTO dto = new ServerIPDTO();
        dto.setId(serverIP.id);
        dto.setServerId(serverIP.serverId);
        dto.setIp(serverIP.ip);
        dto.setCreatedDate(serverIP.createdDate);
        dto.setLastModifiedDate(serverIP.lastModifiedDate);
        return dto;
    }

    public static List<ServerPingDTO> toServerPingDTOs(List<ServerPing> serverPings) {
        return serverPings.stream().map(DTOMapper::toServerPingDTO).collect(Collectors.toList());
    }

    public static ServerPingDTO toServerPingDTO(ServerPing serverPing) {
        ServerPingDTO dto = new ServerPingDTO();
        dto.setTime(serverPing.serverPingKey.time);
        dto.setServerId(serverPing.serverPingKey.serverId);
        dto.setPlayerCount(serverPing.playerCount);
        return dto;
    }

    public static List<ServerCountDTO> toServerCountDTOs10M(List<ServerPing10M> serverPing10Ms) {
        return serverPing10Ms.stream().map(DTOMapper::toServerCountDTO).collect(Collectors.toList());
    }

    public static ServerCountDTO toServerCountDTO(ServerPing10M ping) {
        ServerCountDTO serverCountDTO = new ServerCountDTO();
        serverCountDTO.setTime(ping.bucket);
        serverCountDTO.setMin(ping.min);
        serverCountDTO.setAvg(ping.avg);
        serverCountDTO.setMax(ping.max);
        return serverCountDTO;
    }

    public static List<ServerCountDTO> toServerCountDTOs1H(List<ServerPing1H> pings) {
        return pings.stream().map(DTOMapper::toServerCountDTO).collect(Collectors.toList());
    }

    public static ServerCountDTO toServerCountDTO(ServerPing1H ping) {
        ServerCountDTO serverCountDTO = new ServerCountDTO();
        serverCountDTO.setTime(ping.bucket);
        serverCountDTO.setMin(ping.min);
        serverCountDTO.setAvg(ping.avg);
        serverCountDTO.setMax(ping.max);
        return serverCountDTO;
    }

    public static List<ServerCountDTO> toServerCountDTOs4H(List<ServerPing4H> pings) {
        return pings.stream().map(DTOMapper::toServerCountDTO).collect(Collectors.toList());
    }

    public static ServerCountDTO toServerCountDTO(ServerPing4H ping) {
        ServerCountDTO serverCountDTO = new ServerCountDTO();
        serverCountDTO.setTime(ping.bucket);
        serverCountDTO.setMin(ping.min);
        serverCountDTO.setAvg(ping.avg);
        serverCountDTO.setMax(ping.max);
        return serverCountDTO;
    }

    public static List<ServerCountDTO> toServerCountDTOs1D(List<ServerPing1D> pings) {
        return pings.stream().map(DTOMapper::toServerCountDTO).collect(Collectors.toList());
    }

    public static ServerCountDTO toServerCountDTO(ServerPing1D ping) {
        ServerCountDTO serverCountDTO = new ServerCountDTO();
        serverCountDTO.setTime(ping.bucket);
        serverCountDTO.setMin(ping.min);
        serverCountDTO.setAvg(ping.avg);
        serverCountDTO.setMax(ping.max);
        return serverCountDTO;
    }
}
