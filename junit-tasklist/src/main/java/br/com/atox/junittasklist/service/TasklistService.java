package br.com.atox.junittasklist.service;

import br.com.atox.junittasklist.dto.Task;
import br.com.atox.junittasklist.exception.TaskNotFoundException;
import br.com.atox.junittasklist.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TasklistService {
    private final TaskRepository taskRepository;



    public TasklistService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.create(task);
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(int id, Task updatedTask) {
        Task taskToUpdate = getTaskById(id).orElseThrow(() -> new TaskNotFoundException("Não existe tarefa com o id informado"));

        if (taskToUpdate != null) {
            taskToUpdate.setTitle(updatedTask.getTitle());
            taskToUpdate.setDescription(updatedTask.getDescription());
        }

        return taskToUpdate;
    }

    public void deleteTask(int id) {
         taskRepository.deleteById(id);
    }
}
