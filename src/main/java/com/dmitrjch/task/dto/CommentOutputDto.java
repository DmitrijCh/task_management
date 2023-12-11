package com.dmitrjch.task.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentOutputDto {
    public UUID id;
    public String comment;
    public String authorId;
}