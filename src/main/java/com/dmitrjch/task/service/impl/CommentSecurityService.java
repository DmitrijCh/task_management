package com.dmitrjch.task.service.impl;

import com.dmitrjch.task.model.Comment;
import com.dmitrjch.task.error.NotFoundException;
import com.dmitrjch.task.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentSecurityService {
    private final CommentService service;

    public boolean userHasRightsToModify(UUID commentId, Jwt currentUser) {
        Comment comment = service.findById(commentId).orElseThrow(() ->
                new NotFoundException("Comment with id '%s' could not be found", commentId));
        String currentUserId = currentUser.getClaimAsString("sub");

        return comment.getAuthorId().equals(currentUserId);
    }
}