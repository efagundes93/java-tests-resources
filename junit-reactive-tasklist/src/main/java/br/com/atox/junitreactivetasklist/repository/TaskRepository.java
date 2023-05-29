package br.com.atox.junitreactivetasklist.repository;

import br.com.atox.junitreactivetasklist.dto.Task;
import br.com.atox.junitreactivetasklist.exception.TaskNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    public Flux<Task> getAllTasks() {
        return Flux.fromIterable(tasks);
    }

    public Mono<Task> save(Task task) {
        tasks.add(task);
        return Mono.just(task);
    }

    public Mono<Task> findById(int id) {
        return Flux.fromIterable(tasks)
                .filter(task -> task.getId() == id)
                .next();
    }

    public Mono<Boolean> deleteById(int id) {
        return Mono.just(tasks.removeIf(task -> task.getId() == id));
    }
}
