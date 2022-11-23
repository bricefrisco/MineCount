package com.minecount.endpoints;

import com.minecount.models.BucketInterval;
import com.minecount.models.dtos.ServerCountDTO;
import com.minecount.services.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/counts")
@Produces("application/json")
@Consumes("application/json")
public class CountsResource {
    Logger LOGGER = LoggerFactory.getLogger(CountsResource.class);

    @Inject
    DatabaseService databaseService;

    @GET
    public List<ServerCountDTO> getBucket(@QueryParam("bucket") String bucket, @QueryParam("serverId") Integer serverId) {
        BucketInterval interval = BucketInterval.fromString(bucket);
        if (interval == null) {
            interval = BucketInterval.TEN_MINUTES;
        }

        switch(interval) {
            case ONE_HOUR -> {
                return databaseService.fetchBucket1H(serverId);
            }

            case FOUR_HOURS -> {
                return databaseService.fetchBucket4H(serverId);
            }

            case ONE_DAY -> {
                return databaseService.fetchBucket1D(serverId);
            }

            default -> {
                return databaseService.fetchBucket10M(serverId);
            }
        }
    }
}
