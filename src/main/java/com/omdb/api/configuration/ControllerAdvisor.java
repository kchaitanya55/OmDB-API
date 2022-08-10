package com.omdb.api.configuration;

import com.omdb.api.exception.util.UnAuthorizedBasicUser;
import com.omdb.api.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UnAuthorizedBasicUser.class})
    public ResponseEntity<Object> handleUnAuthorizedBasicUserException(Exception exception) {
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleOtherException() {
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage("Internal Server Error");
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return null;
    }
}