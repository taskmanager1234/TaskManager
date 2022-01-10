package com.company.model;

import com.company.constants.Tables;
import com.company.exception.NoSuchTaskException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;



public class TasksJournal implements Serializable {
    private Map<UUID, Task> tasks;

    public TasksJournal(UUID id,Map<UUID, Task> tasks) {
        this.tasks = tasks;
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }

    public TasksJournal() {
        this.tasks = new HashMap<>();
        this.id = UUID.randomUUID();
    }

    public TasksJournal(UUID id, List<Task> tasks) {
        this.tasks = tasks.stream().collect(Collectors.toMap(Task::getId, Function.identity()));
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }

    @Id
    private UUID id;

    public Map<UUID, Task> getTasks() {
        return tasks;
    }

    public void addTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    public void removeTask(UUID index) {
        tasks.remove(index);
    }

    //todo почти никогда не стоит выбрасывать просто Exception - это признак ленивого программиста,
    // который не подумал, а в каком сценарии у меня может произойти ошибка и как эта ошибка должна хендлиться. RESOLVED
    public Task getTaskById(UUID id) throws NoSuchTaskException {
        //todo этот код не вызывает никаких вопросов? RESOLVED
        if (tasks.containsKey(id))
            return tasks.get(id);

        throw new NoSuchTaskException("Task with id = " + id + "not found");
    }


    public void updateTaskByID(UUID id, Task task) throws NoSuchTaskException {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
            return;
        }
        throw new NoSuchTaskException("Task with id = " + id + "not found");
    }

    public boolean containsTask(Task task) {
        return tasks.containsValue(task);

    }

    public boolean containsTask(UUID id) {
        return tasks.containsKey(id);

    }

    @Override
    public String toString() {
        return "TasksJournal{" +
                "task=" + tasks +
                "}";
    }

    public void setTasks(Map<UUID, Task> tasks) {
        this.tasks = tasks;
    }
}
