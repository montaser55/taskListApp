package com.example.tasklistapp.mappers;

import com.example.tasklistapp.domain.dto.TaskListDto;
import com.example.tasklistapp.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
