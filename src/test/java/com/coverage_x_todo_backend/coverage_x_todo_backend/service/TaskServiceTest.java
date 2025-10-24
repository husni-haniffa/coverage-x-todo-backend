package com.coverage_x_todo_backend.coverage_x_todo_backend.service;

import com.coverage_x_todo_backend.coverage_x_todo_backend.exception.BadRequestException;
import com.coverage_x_todo_backend.coverage_x_todo_backend.exception.NotFoundException;
import com.coverage_x_todo_backend.coverage_x_todo_backend.model.Task;
import com.coverage_x_todo_backend.coverage_x_todo_backend.model.TaskStatus;
import com.coverage_x_todo_backend.coverage_x_todo_backend.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("Task Service Test")
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Nested
    @DisplayName("Create a ToDo")
    class CreateTodoTask {
        @Test
        @DisplayName("Should create a ToDo")
        void shouldCreateAToDO() {
            String title = "Test ToDo App";
            String description = "Write tests";
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(TaskStatus.CREATED);

            Mockito.when(taskRepository.save(task)).thenReturn(task);

            Task createdTask = taskService.createTask(task);
            assertNotNull(createdTask);
            assertEquals(TaskStatus.CREATED, createdTask.getStatus());
        }

        @Test
        @DisplayName("Should throw bad request if title is empty")
        void shouldThrowBadRequestIfTitleIsEmpty(){
            Task task = new Task();
            task.setTitle("");
            task.setDescription("Description is not empty");

            assertThrows(BadRequestException.class, () -> taskService.createTask(task));
        }

        @Test
        @DisplayName("Should throw bad request if description is empty")
        void shouldThrowBadRequestIfDescriptionIsEmpty(){
            Task task = new Task();
            task.setTitle("title is not empty");
            task.setDescription("");

            assertThrows(BadRequestException.class, () -> taskService.createTask(task));
        }
    }

    @Nested
    @DisplayName("Update a task")
    class UpdateATask {
        @Test
        @DisplayName("Should update task status completed when task exists")
        void shouldUpdateATaskToCompleted(){
            Long id = 1L;
            Task existingTask = new Task();
            existingTask.setId(id);
            existingTask.setTitle("Updating Task");
            existingTask.setDescription("Updating task status");
            existingTask.setStatus(TaskStatus.COMPLETED);

            Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));

            Mockito.when(taskRepository.save(existingTask)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

            Task updatedTask = taskService.updateTaskStatus(id);
            assertNotNull(updatedTask);
            assertEquals(TaskStatus.COMPLETED, updatedTask.getStatus());

        }
        @Test
        @DisplayName("Should throw error if resource not found")
        void shouldThrowNotFoundException(){
            Long id = 0L;
            Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> taskService.updateTaskStatus(id));
        }
    }

    @Nested
    @DisplayName("Get All Pending Tasks")
    class GetAllPendingTasks {

        @Test
        @DisplayName("Should return top 5 created tasks")
        void shouldReturnTop5CreatedTasks() {

            List<Task> existingTasks = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                Task task = new Task();
                task.setId((long) i);
                task.setTitle("Task " + i);
                task.setDescription("Task " + i + " description");
                task.setStatus(TaskStatus.CREATED);
                existingTasks.add(task);
            }

            List<Task> top5Tasks = existingTasks.subList(0, 5);
            Mockito.when(taskRepository.findTop5ByStatus(TaskStatus.CREATED)).thenReturn(top5Tasks);

            List<Task> result = taskService.getAllPendingTasks();

            assertNotNull(result);
            assertEquals(5, result.size());
            assertEquals(TaskStatus.CREATED, result.getFirst().getStatus());
        }
    }
}