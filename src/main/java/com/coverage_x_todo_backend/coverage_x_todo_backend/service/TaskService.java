package com.coverage_x_todo_backend.coverage_x_todo_backend.service;

import com.coverage_x_todo_backend.coverage_x_todo_backend.model.Task;
import com.coverage_x_todo_backend.coverage_x_todo_backend.model.TaskStatus;
import com.coverage_x_todo_backend.coverage_x_todo_backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
        task.setStatus(TaskStatus.CREATED);
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }

    public List<Task> getAllPendingTasks() {
        return taskRepository.findTop5ByStatus(TaskStatus.CREATED);
    }
}
