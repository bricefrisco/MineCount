package com.minecount.routes;

import com.minecount.services.DatabaseService;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SchedulerRoute extends RouteBuilder {
    @Inject
    DatabaseService databaseService;

    @Override
    public void configure() {
        from("cron:server-ping-scheduler?schedule=0+0/5+*+*+*+?")
        // from("timer:test?repeatCount=1")
                .routeId("server-ping-scheduler")
                .log("Scheduler started to ping servers")
                .bean(databaseService, "fetchAllApprovedServers")
                .split().body()
                    .to("seda:ping-server?blockWhenFull=true");

        from("cron:total-counts-scheduler?schedule=0+0/5+*+*+*+?")
                .routeId("total-counts-scheduler")
                .bean(databaseService, "writeTotalPing")
                .log("Successfully pinged all servers and counted ${body} players from all servers.");
    }
}
