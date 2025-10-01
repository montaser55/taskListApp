package com.example.tasklistapp.mappers;

import com.example.tasklistapp.domain.dto.TaskDto;
import com.example.tasklistapp.domain.entities.Task;

public interface TaskMapper {

    TaskDto toDto(Task task);

    Task fromDto(TaskDto taskDto);
}
