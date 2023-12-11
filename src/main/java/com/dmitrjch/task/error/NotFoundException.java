package com.dmitrjch.task.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message, Object... format) {
        super(HttpStatus.NOT_FOUND, message, format);
    }
}