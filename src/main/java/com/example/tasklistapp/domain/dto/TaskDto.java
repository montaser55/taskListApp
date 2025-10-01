package com.example.tasklistapp.domain.dto;

import com.example.tasklistapp.domain.entities.TaskPriority;
import com.example.tasklistapp.domain.entities.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority
) {
}