package com.dmitrjch.task.exception;

import com.dmitrjch.task.dto.error.ErrorDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ErrorDto handleHttpException(HttpException e, HttpServletResponse response) {
        response.setStatus(e.getStatus().value());
        log.warn("{} произошло с причиной: {}", e.getClass().getSimpleName(), e.getMessage());
        return ErrorDto.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(field, errorMessage);
        });
        log.warn("Ошибка ввода - ошибки валидации: {}", errors);

        return new ErrorDto("Ошибка валидации входных данных", errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorDto handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String requiredType = Objects.nonNull(e.getRequiredType()) ? e.getRequiredType().getSimpleName() : "unknown";
        String argumentName = e.getName();
        Map<String, String> errors = new HashMap<>(2);
        errors.put("expectedType", requiredType);
        errors.put("argument", argumentName);
        log.warn("Ошибка ввода - несоответствие типов: {}", errors);

        return new ErrorDto("Несоответствие типа аргумента", errors);
    }
}