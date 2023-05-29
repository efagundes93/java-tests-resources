package br.com.atox.junittasklist.repository;

import br.com.atox.junittasklist.dto.Task;
import br.com.atox.junittasklist.exception.DuplicatedTaskException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TaskRepository {

    private static final List<Task> tasks = new ArrayList<Task>();

    public List<Task> findAll() {
        return tasks;
    }

    public static int getNextId(){
        return tasks.size();
    }
    public Task create(Task task) {
        var exists = exists(task);
        if(exists){
            throw new DuplicatedTaskException("task duplicada");
        }
        task.setId(getNextId());
        tasks.add(task);
        return task;
    }

    public boolean exists(Task task){
        return tasks.stream().anyMatch(savedTask -> savedTask.getTitle().equals(task.getTitle()) && savedTask.getDescription().equals(task.getDescription()));
    }
    public Optional<Task> findById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    public boolean deleteById(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }
}
