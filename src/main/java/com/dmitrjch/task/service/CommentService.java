package com.dmitrjch.task.service;

import com.dmitrjch.task.model.Comment;
import com.dmitrjch.task.model.Task;

import java.util.UUID;

public interface CommentService extends CrudService<Comment, UUID> {
    Comment updateById(Task task, UUID commentId, Comment comment);
}