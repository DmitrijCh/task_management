package com.dmitrjch.task.mapper;

import com.dmitrjch.task.config.MapConfig;
import com.dmitrjch.task.dto.CreateTaskDto;
import com.dmitrjch.task.dto.TaskOutputDto;
import com.dmitrjch.task.model.Task;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class, uses = {CommentMapper.class})
public abstract class TaskMapper {
    public abstract Task fromDto(CreateTaskDto dto);

    public abstract TaskOutputDto toDto(Task task);
}