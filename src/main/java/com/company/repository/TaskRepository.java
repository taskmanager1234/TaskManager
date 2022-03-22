package com.company.repository;

import com.company.model.Task;
import com.company.service.SearchService;
import com.company.service.SearchService.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        return (Task) entityManager.createNativeQuery("select task.* from task" +
                "    where id = :id", Task.class)
                .setParameter(QueryParameters.ID, id.toString()).getSingleResult();
    }

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
    public void updateJournalIdInTasks(UUID id, String ids) {

         entityManager.createNativeQuery("UPDATE task set journal_id = :id where task.id in (:ids)  ", Task.class).
                 setParameter(QueryParameters.ID, id.toString()).
                 setParameter("ids", ids)
                 .executeUpdate();
    }

    public Task getTaskByTaskId(UUID taskId) {
        String query = String.format("select task.* from task " +
                        "where task.id = :%s",
                QueryParameters.TASK_ID);
        return (Task) entityManager.createNativeQuery(query, Task.class)
                .setParameter(QueryParameters.TASK_ID, taskId.toString()).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTasksByCondition(SearchService.Criterion criterion, String value, UUID journalId) {


        if (criterion.getCondition().equals(Condition.CONTAINS) || criterion.getCondition().equals(Condition.NOT_CONTAINS))
            value = "%" + value + "%";

        String query = "select task.* from task where " + criterion.getCriterionString();

        return (List<Task>) entityManager.createNativeQuery(
                query, Task.class)
                .setParameter(QueryParameters.VALUE, value)
                .setParameter(QueryParameters.TASK_JOURNAL_ID, journalId.toString()).getResultList();

    }


//    public List<Task> getTasksByJournalId(UUID id) {
//       // return entityManager.createNativeQuery("SELECT * FROM Task WHERE tasks_journal_id = :id").setParameter("id", id).getResultList();
//        return entityManager.createNativeQuery("SELECT t.* FROM Task t " +
//                " inner join journal_tasks_mapping jtm on jtm.task_id = t.id " +
//                "WHERE jtm.journal_id = :id ", Task.class).setParameter("id", id.toString() ).getResultList();
//    }
}