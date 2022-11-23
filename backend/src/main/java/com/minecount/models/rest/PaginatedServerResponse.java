package com.minecount.models.rest;

import com.minecount.models.dtos.ServerDTO;

import java.util.List;

public class PaginatedServerResponse {
    private Integer pageCount;
    private Long count;
    private List<ServerDTO> servers;

    public Integer getPageCount() {
        return pageCount;
    }

    public Long getCount() {
        return count;
    }

    public List<ServerDTO> getServers() {
        return servers;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setServers(List<ServerDTO> servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return "PaginatedServerResponse{" +
                "pageCount=" + pageCount +
                ", count=" + count +
                ", servers=" + servers +
                '}';
    }
}
