package com.company;

import java.util.ArrayList;
import java.util.List;

public class TasksJournal {
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

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void updateTask(Task task, int index) {
        tasks.set(index, task);
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
