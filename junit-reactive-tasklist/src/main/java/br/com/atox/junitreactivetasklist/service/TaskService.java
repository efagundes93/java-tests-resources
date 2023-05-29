package br.com.atox.junitreactivetasklist.service;

import br.com.atox.junitreactivetasklist.dto.Task;
import br.com.atox.junitreactivetasklist.exception.TaskNotFoundException;
import br.com.atox.junitreactivetasklist.repository.TaskRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Flux<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Mono<Task> createTask(Task task) {
        return taskRepository.save(task);
    }

    public Mono<Task> getTaskById(int id) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found with ID: " + id)));
    }

    public Mono<Task> updateTask(int id, Task updatedTask) {
        return getTaskById(id)
                .flatMap(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    return Mono.defer(() -> taskRepository.deleteById(id))
                            .flatMap(aVoid -> taskRepository.save(task));
                });
    }

    public Mono<Void> deleteTask(int id) {
        return taskRepository.deleteById(id)
                .flatMap(aboolean -> Mono.empty());
    }
}
