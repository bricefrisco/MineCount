package com.minecount.services.exceptions;

import com.minecount.models.exceptions.BadGatewayException;
import com.minecount.models.exceptions.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.Timestamp;

@Provider
public class BadGatewayExceptionHandler implements ExceptionMapper<BadGatewayException> {
    @Override
    public Response toResponse(BadGatewayException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatusCode(Response.Status.BAD_GATEWAY.getStatusCode());
        errorResponse.setStatusMessage(Response.Status.BAD_GATEWAY.getReasonPhrase());
        errorResponse.setErrorMessage(e.getMessage());
        return Response.status(Response.Status.BAD_GATEWAY).entity(errorResponse).build();
    }
}
