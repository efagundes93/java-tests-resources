package br.com.atox.junittasklist.api;

import br.com.atox.junittasklist.dto.Task;
import br.com.atox.junittasklist.exception.TaskNotFoundException;
import br.com.atox.junittasklist.service.TasklistService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasklistApi {
    private final TasklistService tasklistService;

    public TasklistApi(TasklistService tasklistService) {
        this.tasklistService = tasklistService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return tasklistService.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return tasklistService.createTask(task);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return tasklistService.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        return tasklistService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
         tasklistService.deleteTask(id);
    }
}
