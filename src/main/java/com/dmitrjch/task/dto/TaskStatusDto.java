package com.dmitrjch.task.dto;

import com.dmitrjch.task.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusDto {
    public TaskStatus status;
}