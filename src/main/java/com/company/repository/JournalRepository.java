package com.company.repository;

import com.company.model.TasksJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

@Repository
public class JournalRepository {
    @Autowired
    private EntityManager entityManager;


    public TasksJournal findById(UUID id) {
        return entityManager.find(TasksJournal.class, id);
    }

//    public TasksJournal findById(UUID id) {
//        return (TasksJournal) entityManager.createQuery("SELECT t FROM TasksJournal t WHERE t.id = :id").setParameter("id", id).getSingleResult();
//
//    }

    @Transactional
    public void create(TasksJournal tasksJournal) {
        entityManager.persist(tasksJournal);
    }

    @Transactional
    public void update(TasksJournal tasksJournal) {
        entityManager.merge(tasksJournal);
    }

    @Transactional
    public void delete(TasksJournal tasksJournal) {
        entityManager.remove(tasksJournal);
    }


}
