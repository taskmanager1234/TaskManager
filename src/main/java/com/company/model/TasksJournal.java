package com.company.model;

import com.company.exception.NoSuchTaskException;
import org.apache.catalina.valves.HealthCheckValve;

import java.io.Serializable;
import java.util.*;

public class TasksJournal implements Serializable {
    private Map<UUID,Task> tasks;

    public TasksJournal(Map<UUID,Task> tasks) {
        this.tasks = tasks;
    }

    public TasksJournal(){
        this.tasks = new HashMap<>();
    }


    public Map<UUID,Task> getTasks() {
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
            if(tasks.containsKey(id))
                return tasks.get(id);

        throw new NoSuchTaskException("Task with id = " + id + "not found");
    }


    public void updateTaskByID(UUID id,Task task) throws NoSuchTaskException {
        if(tasks.containsKey(id))
            tasks.put(id, task);
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
}
