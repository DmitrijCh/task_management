package com.dmitrjch.task.service;

import com.dmitrjch.task.model.Task;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TaskService extends PageableCrudService<Task, UUID> {
    Page<Task> findAllByAuthor(String authorId);
    Page<Task> findAllByAssignee(String assigneeId);
}