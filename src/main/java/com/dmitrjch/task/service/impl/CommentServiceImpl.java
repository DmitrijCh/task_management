package com.dmitrjch.task.service.impl;

import com.dmitrjch.task.exception.NotFoundException;
import com.dmitrjch.task.model.Comment;
import com.dmitrjch.task.model.Task;
import com.dmitrjch.task.repository.CommentRepository;
import com.dmitrjch.task.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment updateById(Task task, UUID commentId, Comment comment) {
        if (task.getComments().stream().noneMatch((c) -> c.getId().equals(commentId))) {
            throw new NotFoundException("Task with id '%s' does not contain comment with id '%s'",
                    task.getId(), commentId);
        }

        comment.setTask(task);
        comment.setId(commentId);

        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        return commentRepository.findById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return commentRepository.existsById(id);
    }

    @Override
    public Comment save(Comment entity) {
        return commentRepository.save(entity);
    }

    @Override
    public Comment updateById(UUID id, Comment entity) {
        entity.setId(id);
        return commentRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        if (!existsById(id)) {
            throw new NotFoundException("Comment with id '%s' does not exist", id);
        }

        commentRepository.deleteById(id);
    }
}