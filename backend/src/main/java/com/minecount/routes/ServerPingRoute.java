package com.minecount.routes;

import com.minecount.services.DatabaseService;
import com.minecount.services.MCPing;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ServerPingRoute extends RouteBuilder {
    @Inject
    DatabaseService databaseService;

    @Override
    public void configure() {
        onException(Exception.class)
                .routeId("server-ping-error")
                .handled(Boolean.TRUE)
                .log(LoggingLevel.WARN, "Exception occurred while processing server with id " +
                        "'${exchangeProperty[id]}' and ip '${exchangeProperty[ip]}': " +
                        "${exception.message} | ${exception.stacktrace}")
                .bean(databaseService, "writeFailedPing(${exchangeProperty[id]})");

        from("seda:ping-server?concurrentConsumers=10")
                .routeId("server-pinger")
                .setProperty("id", simple("${body.id}"))
                .setProperty("ip", simple("${body.ip}"))
                .bean(MCPing.class, "getPing(${body.ip})")
                .log("Pinging server '${exchangeProperty[id]}-${exchangeProperty.ip}'...")
                .bean(databaseService, "writePing(${exchangeProperty[id]}, ${body})")
                .log("Successfully pinged '${exchangeProperty[id]}-${exchangeProperty.ip}': ${body} players online.");
    }
}
