package com.company.service;

import com.company.exception.DeleteTaskException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.repository.JournalRepository;
import com.company.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TaskJournalService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    JournalRepository journalRepository;

    public TasksJournal getById(UUID id) {

        return journalRepository.findById(id);
    }


    public List<TasksJournal> getJournalsByUserId(UUID userId) {
        return journalRepository.getJournalsByUserId(userId);
    }

    public List<TasksJournal> getJournals() {
        return journalRepository.getJournals();
    }


    public void create(TasksJournal tasksJournal) {
        List<Task> tasks = new ArrayList<>(tasksJournal.getTasks());
        taskRepository.createAll(tasks);
        journalRepository.create(tasksJournal);
    }

    public void deleteJournalById(UUID id) throws DeleteTaskException {
        TasksJournal journal = journalRepository.findById(id);
        if (Objects.isNull(journal))
            throw new DeleteTaskException("No such task found with id" + id);
        journalRepository.delete(journal);
    }

    public TaskJournalService() {
    }



}
