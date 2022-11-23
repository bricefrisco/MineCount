package com.minecount.endpoints;

import com.minecount.models.Claim;
import com.minecount.models.MCPingResponse;
import com.minecount.models.database.Server;
import com.minecount.models.dtos.ServerDTO;
import com.minecount.models.exceptions.BadGatewayException;
import com.minecount.models.exceptions.BadRequestException;
import com.minecount.models.exceptions.NotFoundException;
import com.minecount.models.exceptions.UnprocessableEntityException;
import com.minecount.models.rest.*;
import com.minecount.services.AuthorizationService;
import com.minecount.services.DTOMapper;
import com.minecount.services.DatabaseService;
import com.minecount.services.MCPing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Path("/servers")
@Consumes("application/json")
@Produces("application/json")
public class ServersResource {
    Logger LOGGER = LoggerFactory.getLogger(ServersResource.class);

    @Inject
    DatabaseService databaseService;

    @GET
    public PaginatedServerResponse fetchServers(@QueryParam("page") Integer pageNumber) {
        if (pageNumber == null) {
            pageNumber = 0;
        }

        return databaseService.fetchApprovedServers(pageNumber);
    }

    @GET
    @Path("/{name}")
    public ServerDTO fetchByName(@PathParam("name") String name) {
        Optional<Server> maybeServer = databaseService.fetchByName(name);
        if (maybeServer.isEmpty()) {
            throw new NotFoundException("Server not found.");
        }

        return DTOMapper.toServerDTO(maybeServer.get());
    }

    @GET
    @Path("/unapproved/{id}")
    public ServerDTO fetchUnapprovedServer(@PathParam("id") Long id) {
        return DTOMapper.toServerDTO(databaseService.fetchUnapprovedServer(id));
    }

    @GET
    @Path("/unapproved")
    public List<ServerDTO> fetchUnapprovedServers(@HeaderParam("Authorization") String token) {
        AuthorizationService.validate(token, Claim.APPROVE_SERVERS);
        return DTOMapper.toServerDTOs(databaseService.fetchUnapprovedServers());
    }

    @GET
    @Path("/no-websites")
    public List<ServerDTO> fetchServersWithoutWebsite(@HeaderParam("Authorization") String token) {
        AuthorizationService.validate(token, Claim.UPDATE_SERVERS);
        return DTOMapper.toServerDTOs(databaseService.fetchServersWithoutWebsites());
    }

    @GET
    @Path("/no-discord")
    public List<ServerDTO> fetchServersWithoutDiscord(@HeaderParam("Authorization") String token) {
        AuthorizationService.validate(token, Claim.UPDATE_SERVERS);
        return DTOMapper.toServerDTOs(databaseService.fetchServersWithoutDiscord());
    }

    @GET
    @Path("/pre-checks/{ip}")
    public DupeCheckResponse dupeCheck(@PathParam("ip") String ip) throws IOException {
        ip = ip.trim().toLowerCase();
        DupeCheckResponse response = new DupeCheckResponse();
        MCPingResponse ping = MCPing.getPing(ip);
        ServerDTO existingServer = databaseService.dupeCheck(ip, ping.getFavicon(), ping.getDescription().getText());
        response.setAlreadyExists(existingServer != null);
        response.setExistingServer(existingServer);
        response.setPing(ping);
        return response;
    }

    @GET
    @Path("/ping/{ip}")
    public MCPingResponse ping(@PathParam("ip") String ip) throws IOException {
        return MCPing.getPing(ip);
    }

    @PUT
    public ServerDTO updateServer(ModifyServerRequest request) {
        if (request == null) {
            throw new BadRequestException("Request cannot be blank.");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Name cannot be blank.");
        }

        if (request.getIp() == null || request.getIp().isBlank()) {
            throw new BadRequestException("IP cannot be blank.");
        }

        return databaseService.updateServer(request);
    }

    @POST
    public ServerDTO createServer(CreateServerRequest request) {
        if (request == null) {
            throw new BadRequestException("Request cannot be blank");
        }

        if (request.getIp() == null || request.getIp().isBlank()) {
            throw new BadRequestException("IP cannot be blank.");
        }

        String ip = request.getIp().trim().toLowerCase();

        MCPingResponse ping;
        try {
            ping = MCPing.getPing(ip);
        } catch (Exception e) {
            LOGGER.warn("Exception occurred while trying to ping '" + ip + "'", e);
            throw new BadGatewayException("Could not ping server.");
        }

        if (ping.getPlayers().getOnline() < 50) {
            throw new UnprocessableEntityException("Server must have 50+ players online.");
        }

        return databaseService.createServer(ip, ping);
    }

    @PUT
    @Path("/approvals")
    public ServerDTO serverRequestApproval(ServerRequestApproval request, @HeaderParam("Authorization") String token) {
        String userId = AuthorizationService.validate(token, Claim.APPROVE_SERVERS);

        if (request == null) {
            throw new BadRequestException("Request cannot be blank.");
        }

        if (request.getId() == null) {
            throw new BadRequestException("Id cannot be blank.");
        }

        if (request.getApproved() == null) {
            throw new BadRequestException("Approved cannot be blank.");
        }

        if (request.getApproved() == Boolean.TRUE && (request.getName() == null || request.getName().isBlank())) {
            throw new BadRequestException("Name cannot be blank.");
        }

        if (request.getApproved() == Boolean.FALSE && (request.getNotes() == null || request.getNotes().isBlank())) {
            throw new BadRequestException("Rejected servers must have the reason in notes.");
        }

        return databaseService.serverRequestApproval(request, userId);
    }
}
