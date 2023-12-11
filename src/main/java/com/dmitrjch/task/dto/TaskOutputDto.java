package com.dmitrjch.task.dto;

import com.dmitrjch.task.model.TaskPriority;
import com.dmitrjch.task.model.TaskStatus;
import lombok.Data;
import java.util.List;
import java.util.UUID;

    @Data
    public class TaskOutputDto {
        public UUID id;
        public String heading;
        public String description;
        public TaskStatus status;
        public TaskPriority priority;
        public String authorId;
        public String assigneeId;
        public List<CommentOutputDto> comments;
    }