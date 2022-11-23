package com.minecount.models.rest;

import com.minecount.models.MCPingResponse;
import com.minecount.models.dtos.ServerDTO;

public class DupeCheckResponse {
    private Boolean alreadyExists;
    private ServerDTO existingServer;
    private MCPingResponse ping;

    public Boolean getAlreadyExists() {
        return alreadyExists;
    }

    public ServerDTO getExistingServer() {
        return existingServer;
    }

    public MCPingResponse getPing() {
        return ping;
    }

    public void setAlreadyExists(Boolean alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public void setExistingServer(ServerDTO existingServer) {
        this.existingServer = existingServer;
    }

    public void setPing(MCPingResponse ping) {
        this.ping = ping;
    }

    @Override
    public String toString() {
        return "DupeCheckResponse{" +
                "alreadyExists=" + alreadyExists +
                ", existingServer=" + existingServer +
                ", ping=" + ping +
                '}';
    }
}
