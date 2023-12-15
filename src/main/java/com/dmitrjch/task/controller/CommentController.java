package com.dmitrjch.task.controller;

import com.dmitrjch.task.dto.CommentOutputDto;
import com.dmitrjch.task.dto.CreateCommentDto;
import com.dmitrjch.task.exception.NotFoundException;
import com.dmitrjch.task.mapper.CommentMapper;
import com.dmitrjch.task.model.Comment;
import com.dmitrjch.task.model.Task;
import com.dmitrjch.task.service.CommentService;
import com.dmitrjch.task.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final TaskService taskService;
    private final CommentMapper mapper;

    @ApiOperation(value = "Создать комментарий",
            notes = "Создать комментарий и связать его с задачей"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Успешно создано", responseCode = "201"),
            @ApiResponse(description = "Неверный запрос (некорректное тело/переменные пути)", responseCode = "400"),
            @ApiResponse(description = "Отсутствует/неверный токен доступа", responseCode = "401"),
            @ApiResponse(description = "Не найдено (дополнительные детали в теле ответа)", responseCode = "404")
    })
    @PostMapping
    public CommentOutputDto createComment(@PathVariable UUID taskId,
                                          @Valid @RequestBody CreateCommentDto dto,
                                          Principal currentUser) {
        Comment comment = mapper.fromDto(dto);
        Task parentTask = taskService.findById(taskId).orElseThrow(() ->
                new NotFoundException("Задача с идентификатором '%s' не может быть найдена", taskId));
        comment.setTask(parentTask);
        comment.setAuthorId(currentUser.getName());
        comment = commentService.save(comment);
        return mapper.toDto(comment);
    }

    @ApiOperation(value = "Обновить комментарий",
            notes = "Обновить содержимое комментария"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Ок", responseCode = "200"),
            @ApiResponse(description = "Неверный запрос (некорректное тело/переменные пути)", responseCode = "400"),
            @ApiResponse(description = "Отсутствует/неверный токен доступа", responseCode = "401"),
            @ApiResponse(description = "Нет доступа", responseCode = "403"),
            @ApiResponse(description = "Не найдено (дополнительные детали в теле ответа)", responseCode = "404")
    })
    @PutMapping("/{commentId}")
    public CommentOutputDto updateComment(@PathVariable UUID taskId, @PathVariable UUID commentId, @Valid @RequestBody CreateCommentDto dto) {
        Comment comment = mapper.fromDto(dto);
        Task parentTask = taskService.findById(taskId).orElseThrow(() ->
                new NotFoundException("Задача с идентификатором '%s' не может быть найдена", taskId));
        comment = commentService.updateById(parentTask, commentId, comment);
        return mapper.toDto(comment);
    }

    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "Удалить комментарий",
            notes = "Удалить комментарий"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Удалено", responseCode = "204"),
            @ApiResponse(description = "Некорректные переменные пути", responseCode = "400"),
            @ApiResponse(description = "Отсутствует/неверный токен доступа", responseCode = "401"),
            @ApiResponse(description = "Нет доступа", responseCode = "403"),
            @ApiResponse(description = "Не найдено (дополнительные детали в теле ответа)", responseCode = "404")
    })
    public void deleteById(@PathVariable UUID taskId, @PathVariable UUID commentId) {
        Task parentTask = taskService.findById(taskId).orElseThrow(() ->
                new NotFoundException("Задача с идентификатором '%s' не может быть найдена", taskId));
        if (parentTask.getComments().stream().noneMatch((c) -> c.getId().equals(commentId))) {
            throw new NotFoundException("Задача с идентификатором '%s' не содержит комментария с идентификатором '%s'", taskId, commentId);
        }
        commentService.deleteById(commentId);
    }
}