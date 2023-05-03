package com.test.be.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ErrorResponse{

    private HttpStatus status;
    private List<String> messages;
    private int code;

    public ErrorResponse(HttpStatus status, int code, List<String> messages) {
        this.status = status;
        this.messages = messages;
        this.code = code;
    }

    public ErrorResponse(HttpStatus status, int code, String message){
        this.status = status;
        this.code = code;
        messages = Arrays.asList(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
