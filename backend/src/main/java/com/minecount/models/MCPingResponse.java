package com.minecount.models;

import com.minecount.services.MCPingUtil;

import java.util.List;

public class MCPingResponse {
    private Description description;
    private Players players;
    private Version version;
    private String favicon;
    private long ping;
    private String hostname;
    private long port;

    public Description getDescription() {
        return description;
    }

    public Players getPlayers() {
        return players;
    }

    public Version getVersion() {
        return version;
    }

    public String getFavicon() {
        return favicon;
    }

    public long getPing() {
        return ping;
    }

    public String getHostname() {
        return hostname;
    }

    public long getPort() {
        return port;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public void setPing(long ping) {
        this.ping = ping;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(long port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "MCPingResponse{" +
                "description=" + description +
                ", players=" + players +
                ", version=" + version +
                ", favicon='" + favicon + '\'' +
                ", ping=" + ping +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }

    public static class Description {
        private String text;

        public String getText() {
            return text;
        }

        public String getStrippedText() {
            return MCPingUtil.stripColors(this.text);
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "Description{" +
                    "text='" + text + '\'' +
                    '}';
        }
    }

    public static class Players {
        private int max;
        private int online;
        private List<Player> sample;

        public int getMax() {
            return max;
        }

        public int getOnline() {
            return online;
        }

        public List<Player> getSample() {
            return sample;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public void setSample(List<Player> sample) {
            this.sample = sample;
        }

        @Override
        public String toString() {
            return "Players{" +
                    "max=" + max +
                    ", online=" + online +
                    ", sample=" + sample +
                    '}';
        }
    }

    public static class Player {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    public class Version {
        private String name;
        private int protocol;

        public String getName() {
            return name;
        }

        public int getProtocol() {
            return protocol;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProtocol(int protocol) {
            this.protocol = protocol;
        }

        @Override
        public String toString() {
            return "Version{" +
                    "name='" + name + '\'' +
                    ", protocol=" + protocol +
                    '}';
        }
    }

}
