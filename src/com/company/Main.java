package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Task task = new Task("Weather", "The Sun is shining", new Date(), new Date());
        Task task2 = new Task("ArrayList", "ArrayList.add", new Date(), new Date());
        System.out.println(task);
        task.setDescription("Sun");

        ArrayList<Task> list = new ArrayList<>();
        list.add(task);
        list.add(task2);
        TasksJournal tasksJournal = new TasksJournal(list);
        System.out.println(tasksJournal.toString());

    }
}
