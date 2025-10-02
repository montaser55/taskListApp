package com.example.tasklistapp.services.impl;

import com.example.tasklistapp.domain.dto.TaskDto;
import com.example.tasklistapp.domain.entities.Task;
import com.example.tasklistapp.domain.entities.TaskList;
import com.example.tasklistapp.domain.entities.TaskPriority;
import com.example.tasklistapp.domain.entities.TaskStatus;
import com.example.tasklistapp.repositories.TaskListRepository;
import com.example.tasklistapp.repositories.TaskRepository;
import com.example.tasklistapp.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null != task.getId()){
            throw new IllegalArgumentException("Task already exists !");
        }

        if(null == task.getTitle() ||  task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task title is null !");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;
        LocalDateTime now = LocalDateTime.now();
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("No tasklist found with id " + taskListId));

        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        ));
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(null == task.getId()){
            throw new IllegalArgumentException("Task id not mentioned !");
        }

        if(!Objects.equals(task.getId(), taskId)){
            throw new IllegalArgumentException("Task id can not be changed !");
        }

        if(!Objects.equals(task.getTaskList().getId(), taskListId)){
            throw new IllegalArgumentException("Task list can not be changed !");
        }

        if(task.getStatus() == null || task.getPriority() == null){
            throw new IllegalArgumentException("Task status or priority can not be null !");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());
        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
