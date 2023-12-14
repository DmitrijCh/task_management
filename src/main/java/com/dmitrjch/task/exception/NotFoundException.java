package com.dmitrjch.task.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {
    public NotFoundException(String message, Object... format) {
        super(HttpStatus.NOT_FOUND, message, format);
    }
}