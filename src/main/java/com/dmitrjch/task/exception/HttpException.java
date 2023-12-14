package com.dmitrjch.task.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {
    private final HttpStatus status;

    public HttpException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpException(HttpStatus status, String message, Object... format) {
        this(message.formatted(format), status);
    }
}