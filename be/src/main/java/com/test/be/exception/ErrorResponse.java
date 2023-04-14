package com.test.be.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponse{

    private HttpStatus status;
    private List<String> message;
    private int code;

    public ErrorResponse(HttpStatus status, int code, List<String> message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
