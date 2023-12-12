package com.dmitrjch.task.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{
    public NotFoundException(String message, Object... format) {
        super(HttpStatus.NOT_FOUND, message, format);
    }
}