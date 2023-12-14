package com.dmitrjch.task.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException {
    public BadRequestException(String message, Object... format) {
        super(HttpStatus.BAD_REQUEST, message, format);
    }
}