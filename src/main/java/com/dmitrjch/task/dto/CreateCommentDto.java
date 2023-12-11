package com.dmitrjch.task.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentDto {
    @NotNull
    @NotEmpty
    public String comment;
}