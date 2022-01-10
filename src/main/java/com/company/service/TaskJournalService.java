package com.company.service;

import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskJournalService {
    @Autowired
    TaskRepository taskRepository;

    public TasksJournal getTasksJournal(){
        //TODO поменять
        return new TasksJournal(null, (List<Task>)taskRepository.findAll());
    }

    public TaskJournalService(){}
}
