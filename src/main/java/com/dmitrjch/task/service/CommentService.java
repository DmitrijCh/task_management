package com.dmitrjch.task.service;

import com.dmitrjch.task.model.Comment;

import java.util.UUID;

public interface CommentService extends PageableCrudService<Comment, UUID> {
}