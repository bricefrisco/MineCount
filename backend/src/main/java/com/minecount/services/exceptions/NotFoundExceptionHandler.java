package com.minecount.services.exceptions;

import com.minecount.models.exceptions.ErrorResponse;
import com.minecount.models.exceptions.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.Timestamp;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
        errorResponse.setStatusMessage(Response.Status.NOT_FOUND.getReasonPhrase());
        errorResponse.setErrorMessage(e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
    }
}
