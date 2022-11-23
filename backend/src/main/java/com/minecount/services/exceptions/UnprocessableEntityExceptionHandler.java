package com.minecount.services.exceptions;

import com.minecount.models.exceptions.ErrorResponse;
import com.minecount.models.exceptions.UnprocessableEntityException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.Timestamp;

@Provider
public class UnprocessableEntityExceptionHandler implements ExceptionMapper<UnprocessableEntityException> {
    @Override
    public Response toResponse(UnprocessableEntityException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatusCode(422);
        errorResponse.setStatusMessage("Unprocessable Entity");
        errorResponse.setErrorMessage(e.getMessage());
        return Response.status(422).entity(errorResponse).build();
    }
}
