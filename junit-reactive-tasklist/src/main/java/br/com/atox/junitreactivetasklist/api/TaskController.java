package br.com.atox.junitreactivetasklist.api;

import br.com.atox.junitreactivetasklist.dto.Task;
import br.com.atox.junitreactivetasklist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public Mono<ResponseEntity<Task>> createTask(@RequestBody Task task) {
        return taskService.createTask(task)
                .map(createdTask -> ResponseEntity.status(HttpStatus.CREATED).body(createdTask));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Task>> getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Task>> updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable int id) {
        return taskService.deleteTask(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}