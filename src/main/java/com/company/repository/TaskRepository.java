package com.company.repository;

import com.company.model.Task;
import com.company.model.TasksJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepository {
    @Autowired
    private EntityManager entityManager;


    public Task findById(UUID id) {
        return entityManager.find(Task.class, id);
    }

    public void create(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    public void createAll(List<Task> tasks) {
        entityManager.getTransaction().begin();
        for (Task task : tasks) {
            entityManager.merge(task);
        }
        entityManager.getTransaction().commit();
    }

    public void update(Task task) {
        entityManager.getTransaction().begin();
        entityManager.merge(task);
        entityManager.getTransaction().commit();
    }

    public void delete(Task task) {
        entityManager.getTransaction().begin();
        entityManager.remove(task);
        entityManager.getTransaction().commit();
    }

    public List<Task> getTasksByJournalId(UUID id) {
       // return entityManager.createNativeQuery("SELECT * FROM Task WHERE tasks_journal_id = :id").setParameter("id", id).getResultList();
        return entityManager.createNativeQuery("SELECT t.* FROM Task t " +
                " inner join journal_tasks_mapping jtm on jtm.task_id = t.id " +
                "WHERE jtm.journal_id = :id ", Task.class).setParameter("id", id.toString() ).getResultList();
    }
}