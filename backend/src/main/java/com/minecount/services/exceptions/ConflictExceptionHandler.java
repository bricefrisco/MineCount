package com.minecount.services.exceptions;

import com.minecount.models.exceptions.ConflictException;
import com.minecount.models.exceptions.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.Timestamp;

@Provider
public class ConflictExceptionHandler implements ExceptionMapper<ConflictException> {
    @Override
    public Response toResponse(ConflictException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatusCode(Response.Status.CONFLICT.getStatusCode());
        errorResponse.setStatusMessage(Response.Status.CONFLICT.getReasonPhrase());
        errorResponse.setErrorMessage(e.getMessage());
        return Response.status(Response.Status.CONFLICT).entity(errorResponse).build();
    }
}
