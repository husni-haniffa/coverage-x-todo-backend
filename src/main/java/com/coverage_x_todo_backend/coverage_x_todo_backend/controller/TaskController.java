package com.coverage_x_todo_backend.coverage_x_todo_backend.controller;

import com.coverage_x_todo_backend.coverage_x_todo_backend.model.Task;
import com.coverage_x_todo_backend.coverage_x_todo_backend.model.TaskStatus;
import com.coverage_x_todo_backend.coverage_x_todo_backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllPendingTasks() {
        return taskService.getAllPendingTasks();
    }

    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id) {
        return taskService.updateTaskStatus(id);
    }
}
