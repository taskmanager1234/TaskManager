package com.company.model;

import java.util.UUID;

public class TaskManagerSingleton {
    private static TaskManagerSingleton INSTANCE;
    private TasksJournal tasksJournal;

    private TaskManagerSingleton() {
    }

    public static TaskManagerSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskManagerSingleton();
            INSTANCE.tasksJournal = new TasksJournal();
        }
        return INSTANCE;
    }

    public TasksJournal getTasksJournal() {
        return tasksJournal;
    }

    public void setTasksJournal(TasksJournal tasksJournal) {
        this.tasksJournal = tasksJournal;
    }

    public void addTask(Task task){
        tasksJournal.addTask(task);
    }

    public void updateTaskById(UUID id, Task task) throws Exception {
        tasksJournal.updateTaskByID(id, task);
    }

    public void removeTask(Task task){
        tasksJournal.removeTask(task);
    }
}
