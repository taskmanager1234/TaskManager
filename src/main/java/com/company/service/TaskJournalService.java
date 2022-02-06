package com.company.service;

import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.repository.JournalRepository;
import com.company.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskJournalService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    JournalRepository journalRepository;

    public TasksJournal getById(UUID id) {

        TasksJournal tasksJournal = journalRepository.findById(id);
//        List<Task> tasksByJournalId = taskRepository.getTasksByJournalId(id);
//        for (Task currentTask : tasksByJournalId) {
//         //   currentTask.setTasksJournalId(tasksJournal);
//            tasksJournal.addTask(currentTask);
   //     }

        return tasksJournal;
    }

    public void create(TasksJournal tasksJournal) {
        List<Task> tasks = new ArrayList<>(tasksJournal.getTasks());
        taskRepository.createAll(tasks);
        journalRepository.create(tasksJournal);
    }

    public TaskJournalService() {
    }



}
