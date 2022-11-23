package com.minecount.models.rest;

public class CreateServerRequest {
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "CreateServerRequest{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
