package com.example.tasklistapp.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
