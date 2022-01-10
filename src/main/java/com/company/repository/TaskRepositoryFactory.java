package com.company.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TaskRepositoryFactory {
    @Autowired
    TaskRepository taskRepository;

    public TaskRepository getTaskRepository(){
        return taskRepository;
    }
}
