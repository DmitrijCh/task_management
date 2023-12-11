package com.dmitrjch.task.dto;

import com.dmitrjch.task.model.TaskPriority;
import com.dmitrjch.task.model.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTaskDto {
    @NotNull
    @NotEmpty
    public String heading;

    public String description;

    @NotNull
    public TaskStatus status;

    @NotNull
    public TaskPriority priority;

    @NotNull
    @NotEmpty
    public String assigneeId;
}