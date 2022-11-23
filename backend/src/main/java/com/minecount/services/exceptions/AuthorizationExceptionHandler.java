package com.minecount.services.exceptions;

import com.minecount.models.exceptions.AuthorizationException;
import com.minecount.models.exceptions.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.Timestamp;

@Provider
public class AuthorizationExceptionHandler implements ExceptionMapper<AuthorizationException> {
    @Override
    public Response toResponse(AuthorizationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatusCode(Response.Status.UNAUTHORIZED.getStatusCode());
        errorResponse.setStatusMessage(Response.Status.UNAUTHORIZED.getReasonPhrase());
        errorResponse.setErrorMessage(Response.Status.UNAUTHORIZED.getReasonPhrase());
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorResponse).build();
    }
}
