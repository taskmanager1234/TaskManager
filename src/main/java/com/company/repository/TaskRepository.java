package com.company.repository;

import com.company.exception.BadCriterionException;
import com.company.model.Task;
import com.company.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepository {
    private static class QueryParameters {
        public static final String ID = "id";
        public static final String TASK_JOURNAL_ID = "journal_id";
        public static final String TASK_ID = "task_id";
        public static final String VALUE = "value";
    }


    @Autowired
    private EntityManager entityManager;

    public Task findById(UUID id) {
        try {
            return (Task) entityManager.createNamedQuery("findById") //TODO приведение
                    .setParameter(QueryParameters.ID, id).getSingleResult();
        } catch(NoResultException e){
            return null;
        }
    }//TODO дублирование метода

    @Transactional
    public void create(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public void createAll(List<Task> tasks) {
        for (Task task : tasks) {
            entityManager.merge(task);
        }
    }

    @Transactional
    public void update(Task task) {
        entityManager.merge(task);
    }

    @Transactional
    public void delete(Task task) {
        entityManager.remove(task);
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTasksByJournalId(UUID id) {
        return entityManager.createNativeQuery("select task.* from task where task.journal_id = :id", Task.class)
                .setParameter(QueryParameters.ID, id.toString()).getResultList();
    }


    @Transactional
    public void updateJournalIdInTasks(String idJournal, List<String> tasksIds) {

         entityManager.createNativeQuery("UPDATE task set journal_id = :id where task.id in (:ids)", Task.class)
                 .setParameter(QueryParameters.ID, idJournal)
                 .setParameter("ids", tasksIds)
                 .executeUpdate();
    }

    public Task getTaskByTaskId(UUID taskId) {

        return (Task) entityManager.createNamedQuery("getTaskByTaskId")
                .setParameter(QueryParameters.TASK_ID, taskId).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTasksByCondition(SearchService.Criterion criterion, String value, UUID journalId) throws BadCriterionException {


        String query = "select task.* from task where " + criterion.getCriterionString();

        return (List<Task>) entityManager.createNativeQuery(
                query, Task.class)
                .setParameter(QueryParameters.VALUE, criterion.getPreparedValue(value))
                .setParameter(QueryParameters.TASK_JOURNAL_ID, journalId.toString()).getResultList();

    }


}