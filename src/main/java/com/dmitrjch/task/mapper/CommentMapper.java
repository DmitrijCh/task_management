package com.dmitrjch.task.mapper;

import com.dmitrjch.task.config.MapConfig;
import com.dmitrjch.task.dto.CommentOutputDto;
import com.dmitrjch.task.dto.CreateCommentDto;
import com.dmitrjch.task.model.Comment;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class)
public abstract class CommentMapper {
    public abstract Comment fromDto(CreateCommentDto dto);

    public abstract CommentOutputDto toDto(Comment comment);
}