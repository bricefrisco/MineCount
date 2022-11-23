package com.minecount.models.rest;

import com.minecount.models.dtos.ServerDTO;
import com.minecount.models.dtos.ServerIPDTO;
import com.minecount.models.dtos.ServerRequestDTO;

public class ServerRequestApprovalResponse {
    private Boolean approved;
    private ServerRequestDTO serverRequestDTO;
    private ServerDTO serverDTO;
    private ServerIPDTO serverIPDTO;

    public Boolean getApproved() {
        return approved;
    }

    public ServerRequestDTO getServerRequestDTO() {
        return serverRequestDTO;
    }

    public ServerDTO getServerDTO() {
        return serverDTO;
    }

    public ServerIPDTO getServerIPDTO() {
        return serverIPDTO;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setServerRequestDTO(ServerRequestDTO serverRequestDTO) {
        this.serverRequestDTO = serverRequestDTO;
    }

    public void setServerDTO(ServerDTO serverDTO) {
        this.serverDTO = serverDTO;
    }

    public void setServerIPDTO(ServerIPDTO serverIPDTO) {
        this.serverIPDTO = serverIPDTO;
    }

    @Override
    public String toString() {
        return "ServerRequestApprovalResponse{" +
                "approved=" + approved +
                ", serverRequestDTO=" + serverRequestDTO +
                ", serverDTO=" + serverDTO +
                ", serverIPDTO=" + serverIPDTO +
                '}';
    }
}
