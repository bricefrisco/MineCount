package com.minecount.services.exceptions;

import com.minecount.models.exceptions.BadRequestException;
import com.minecount.models.exceptions.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.Timestamp;

@Provider
public class BadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
        errorResponse.setStatusMessage(Response.Status.BAD_REQUEST.getReasonPhrase());
        errorResponse.setErrorMessage(e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}
