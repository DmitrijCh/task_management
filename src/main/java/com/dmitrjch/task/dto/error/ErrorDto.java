package com.dmitrjch.task.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    public String message;

    @Builder.Default
    public Map<String, String> errors = Collections.emptyMap();
}