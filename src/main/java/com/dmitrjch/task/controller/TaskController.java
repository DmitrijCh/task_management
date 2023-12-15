package com.dmitrjch.task.controller;

import com.dmitrjch.task.dto.CreateTaskDto;
import com.dmitrjch.task.dto.TaskOutputDto;
import com.dmitrjch.task.dto.TaskStatusDto;
import com.dmitrjch.task.exception.NotFoundException;
import com.dmitrjch.task.mapper.TaskMapper;
import com.dmitrjch.task.model.Task;
import com.dmitrjch.task.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;
    private final TaskMapper mapper;

    @ApiOperation(value = "Получить все задачи",
            notes = "Получить задачи на основе параметров запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Успешно", responseCode = "200"),
            @ApiResponse(description = "Отсутствует/неверный токен доступа", responseCode = "401"),
    })
    @GetMapping
    public Page<TaskOutputDto> getTasks(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toDto);
    }

    @ApiOperation(value = "Получить задачу",
            notes = "Получить конкретную задачу по идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Успешно", responseCode = "200"),
            @ApiResponse(description = "Неверный путь переменной", responseCode = "400"),
            @ApiResponse(description = "Отсутствует/неверный токен доступа", responseCode = "401"),
            @ApiResponse(description = "Не найдено (подробности в теле ответа)", responseCode = "404")
    })
    @GetMapping("/{id}")
    public TaskOutputDto getTaskById(@PathVariable UUID id) {
        Task task = service.findById(id)
                .orElseThrow(() -> new NotFoundException("Задача с идентификатором '%s' не может быть найдена", id));

        return mapper.toDto(task);
    }

    @ApiOperation(value = "Создать задачу",
            notes = "Создать задачу с данными, предоставленными в теле запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Создано", responseCode = "201"),
            @ApiResponse(description = "Недопустимое тело запроса", responseCode = "400"),
            @ApiResponse(description = "Отсутствующий/недопустимый токен доступа", responseCode = "401")
    })
    @PostMapping
    public TaskOutputDto createTask(@Valid @RequestBody CreateTaskDto dto, Principal currentUser) {
        Task task = mapper.fromDto(dto);
        task.setAuthorId(currentUser.getName());
        task = service.save(task);
        return mapper.toDto(task);
    }

    @ApiOperation(value = "Редактировать задачу",
            notes = "Обновить задачу с данными, предоставленными в теле запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Ок", responseCode = "200"),
            @ApiResponse(description = "Недопустимое тело запроса/переменные пути", responseCode = "400"),
            @ApiResponse(description = "Отсутствующий/недопустимый токен доступа", responseCode = "401"),
            @ApiResponse(description = "Отсутствие доступа", responseCode = "403"),
            @ApiResponse(description = "Не найдено (дополнительные сведения в теле ответа)", responseCode = "404")
    })
    @PutMapping("/{id}")
    public TaskOutputDto updateTask(@PathVariable UUID id, @Valid @RequestBody CreateTaskDto dto) {
        Task task = mapper.fromDto(dto);
        task = service.updateById(id, task);
        return mapper.toDto(task);
    }

    @ApiOperation(value = "Обновить статус задачи",
            notes = "Изменить статус задачи. Предоставленный статус должен отличаться от существующего"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Ок", responseCode = "200"),
            @ApiResponse(description = "Недопустимое тело запроса/переменные пути", responseCode = "400"),
            @ApiResponse(description = "Отсутствующий/недопустимый токен доступа", responseCode = "401"),
            @ApiResponse(description = "Отсутствие доступа", responseCode = "403"),
            @ApiResponse(description = "Не найдено (дополнительные сведения в теле ответа)", responseCode = "404")
    })
    @PatchMapping("/{id}")
    public TaskOutputDto editTaskStatus(@PathVariable UUID id, @Valid @RequestBody TaskStatusDto dto) {
        Task task = service.changeStatus(id, dto.status);
        return mapper.toDto(task);
    }

    @ApiOperation(value = "Удалить задачу",
            notes = "Удалить задачу по идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Удалено", responseCode = "204"),
            @ApiResponse(description = "Недопустимая переменная пути", responseCode = "400"),
            @ApiResponse(description = "Отсутствующий/недопустимый токен доступа", responseCode = "401"),
            @ApiResponse(description = "Отсутствие доступа", responseCode = "403"),
            @ApiResponse(description = "Не найдено (дополнительные сведения в теле ответа)", responseCode = "404")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID id) {
        service.deleteById(id);
    }
}