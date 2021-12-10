package com.company;

import com.company.model.TasksJournal;

public class JournalContext {

    private static TasksJournal tasksJournal;


    public static TasksJournal getTasksJournal() {
        return tasksJournal;
    }

    public static void setTasksJournal(TasksJournal tasksJournal) {
        JournalContext.tasksJournal = tasksJournal;
    }
}
