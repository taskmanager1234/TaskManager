package com.company.model;

import com.company.constants.Tables;
import com.company.exception.NoSuchTaskException;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "task_journal", schema = "public", catalog = "postgres")
public class TasksJournal implements Serializable {

    private UUID id;

    @Transient
    private transient Map<UUID, Task> tasks;

    public TasksJournal(UUID id, Map<UUID, Task> tasks) {
        this.tasks = tasks;
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }

    public TasksJournal() {
        this.tasks = new HashMap<>();
    }

    public TasksJournal(UUID id, List<Task> tasks) {
        this.tasks = tasks.stream().collect(Collectors.toMap(Task::getId, Function.identity()));
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }

    @Id
    @Column(name = "id", columnDefinition = "varchar(40)")
    @Type(type = "uuid-char")
    public UUID getId() {
        return id;
    }

    @Transient
    public Map<UUID, Task> getTasks() {
        return tasks;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    public void removeTask(UUID index) {
        tasks.remove(index);
    }

    public Task getTaskById(UUID id) throws NoSuchTaskException {
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
