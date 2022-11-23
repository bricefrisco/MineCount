package com.minecount.models;

import com.google.common.base.Charsets;

public class MCPingOptions {
    private String hostname;
    private String charset = Charsets.UTF_8.displayName();
    private int port = 25565;
    private int timeout = 5000;
    private int protocolVersion = 4;

    public MCPingOptions() {
    }

    public MCPingOptions(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public String getCharset() {
        return charset;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
}
