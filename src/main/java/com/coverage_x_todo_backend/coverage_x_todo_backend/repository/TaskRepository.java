package com.coverage_x_todo_backend.coverage_x_todo_backend.repository;

import com.coverage_x_todo_backend.coverage_x_todo_backend.model.Task;
import com.coverage_x_todo_backend.coverage_x_todo_backend.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTop5ByStatus(TaskStatus status);
}
