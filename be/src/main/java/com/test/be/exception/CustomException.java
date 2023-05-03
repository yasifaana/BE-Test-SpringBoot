package com.test.be.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleException(NullPointerException e){
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("status",HttpStatus.NOT_FOUND.name());
        errorResponse.put("code",30000);
        errorResponse.put("message","Cannot find resource with id "+e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleException(HttpClientErrorException e){
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("status",HttpStatus.CONFLICT.name());
        errorResponse.put("code",30001);
        errorResponse.put("message","Record with unique value "+e.getStatusText()+" already exists in the system");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e){
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("status",HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.put("code",80000);
        errorResponse.put("message","System error, we're unable to process your request at the moment");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("status", HttpStatus.BAD_REQUEST.name());
        errorResponse.put("code", 30002);

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(x -> x.getDefaultMessage()+x.getRejectedValue())
                .collect(Collectors.toList());
        errorResponse.put("message",errors);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
