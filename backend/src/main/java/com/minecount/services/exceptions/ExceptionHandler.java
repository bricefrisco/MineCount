package com.minecount.services.exceptions;

import com.minecount.models.exceptions.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.Timestamp;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);
    @Override
    public Response toResponse(Exception e) {
        LOGGER.error("", e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        errorResponse.setStatusMessage(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponse.setErrorMessage(e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    }
}
