package com.company.service;

import com.company.exception.CreateTaskException;
import com.company.exception.TaskNotFoundException;
import com.company.model.Task;
import com.company.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getById(UUID id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id);
        if (Objects.isNull(task))
            throw new TaskNotFoundException("No such task found with id" + id);
        return task;
    }

    public void create(Task task) throws CreateTaskException {
        if (task.getEndDate().isBefore(task.getStartDate()))
            throw new CreateTaskException("Could not create task: end date must be after start date");
        taskRepository.create(task);
    }

    public void updateAll(List<Task> tasks){
        taskRepository.updateAll(tasks);
    }

    public void updateJournalIdInTasks(String journalIdForMoveTasks, List<String> ids) {
        taskRepository.updateJournalIdInTasks(journalIdForMoveTasks, ids);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }

    public void update(UUID idJournal){

    }

    public void deleteTaskById(UUID id) {
        Task task = taskRepository.findById(id);
        if(Objects.isNull(task)){
            return;
        }
        taskRepository.delete(task);
    }

    public Task getByIdAndByJournalId(UUID taskId) {
        return taskRepository.getTaskByTaskId(taskId);
    }




}
