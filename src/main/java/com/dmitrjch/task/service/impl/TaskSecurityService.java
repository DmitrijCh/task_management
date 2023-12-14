package com.dmitrjch.task.service.impl;

import com.dmitrjch.task.model.Task;
import com.dmitrjch.task.exception.NotFoundException;
import com.dmitrjch.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskSecurityService {
    private final TaskService taskService;

    public boolean userHasRightsToModify(UUID taskId, Jwt currentUser) {
        Task task = taskService.findById(taskId).orElseThrow(() ->
                new NotFoundException("Task with id '%s' could not be found"));
        String currentUserId = currentUser.getClaimAsString("sub");

        return task.getAuthorId().equals(currentUserId);
    }

    public boolean userHasRightsToChangeStatus(UUID taskId, Jwt currentUser) {
        Task task = taskService.findById(taskId).orElseThrow(() ->
                new NotFoundException("Task with id '%s' could not be found"));
        String currentUserId = currentUser.getClaimAsString("sub");

        return task.getAuthorId().equals(currentUserId) ||
                task.getAssigneeId().equals(currentUserId);
    }
}