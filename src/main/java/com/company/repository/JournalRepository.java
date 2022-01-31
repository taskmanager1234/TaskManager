package com.company.repository;

import com.company.model.Task;
import com.company.model.TasksJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public void create(TasksJournal tasksJournal) {
        entityManager.getTransaction().begin();
        entityManager.persist(tasksJournal);
        entityManager.getTransaction().commit();
    }

    public void update(TasksJournal tasksJournal) {
        entityManager.getTransaction().begin();
        entityManager.merge(tasksJournal);
        entityManager.getTransaction().commit();
    }

    public void delete(TasksJournal tasksJournal) {
        entityManager.getTransaction().begin();
        entityManager.remove(tasksJournal);
        entityManager.getTransaction().commit();
    }


}
