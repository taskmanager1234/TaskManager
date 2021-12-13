package com.company.model;

import org.apache.catalina.valves.HealthCheckValve;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TasksJournal implements Serializable {
    private List<Task> tasks;

    public TasksJournal(List<Task> tasks) {
        this.tasks = tasks;
    }

    public TasksJournal(){
        this.tasks = new ArrayList<>();
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    public void removeTask(int index) {
        tasks.remove(index);
    }

    public Task getTaskById(UUID id) throws Exception {
        for(Task task:tasks){
            if(task.getId().equals(id))
                return task;
        }
        throw new Exception("Task with id = " + id + "not found");
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void updateTaskByID(UUID id,Task task) throws Exception {
      Task curTask = getTaskById(id);
      curTask.setTitle(task.getTitle());
      curTask.setDescription(task.getDescription());
      curTask.setEndDate(task.getEndDate());
    }

    public boolean containsTask(Task task) {
        return tasks.contains(task);

    }

    @Override
    public String toString() {
        return "TasksJournal{" +
                "task=" + tasks +
                "}";
    }
}
