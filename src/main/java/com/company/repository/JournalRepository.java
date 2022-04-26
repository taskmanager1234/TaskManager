package com.company.repository;

import com.company.model.TasksJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
public class JournalRepository {


    @Autowired
    private EntityManager entityManager;

    private static class QueryParameters {
        public static final String USER_ID = "user_id";
    }
    private static class NamedQuery{
        public static final String JOURNALS_USER = "getJournalsByUserId";
    }

    public TasksJournal findById(UUID id) {
        return entityManager.find(TasksJournal.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<TasksJournal> getJournalsByUserId(UUID userId) {
        //todo: почему не используешь createNamedQuery и TypedQuery? С ними код чище
        return (List<TasksJournal>) entityManager.createNamedQuery(NamedQuery.JOURNALS_USER)
                .setParameter(QueryParameters.USER_ID, userId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<TasksJournal> getJournals() {
        return entityManager.createNamedQuery("getJournals").getResultList();
    }

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
