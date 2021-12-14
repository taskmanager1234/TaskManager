package com.company.model;

import java.util.UUID;

//todo отражать в имене, что он singleton, не обязательно

//todo мне кажется, назначение этого класса сейчас достаточно размытое. По факту, нам нужен класс, котрый будет хранить объект журнала, с которым мы работаем, в памяти.
//и жизненный цикл журнала будет таким: загружен из файла, десериализован, помещен в in-memory хранилище. Его можно из этого хранилища получить, поменять. И в конце работы программы то, что лежит в in-memory хранилище, снова сохраняется на диск.
//И программа не должна давать возможностей сделать что-то иначе, только задуманным программистом образом.
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
