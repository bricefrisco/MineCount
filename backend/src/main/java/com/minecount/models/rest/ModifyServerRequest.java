package com.minecount.models.rest;

public class ModifyServerRequest {
    private Long id;
    private String ip;
    private String name;
    private String website;
    private String discord;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getWebsite() {
        return website;
    }

    public String getDiscord() {
        return discord;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    @Override
    public String toString() {
        return "ModifyServerRequest{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", discord='" + discord + '\'' +
                '}';
    }
}
