package com.company.repository;

import com.company.model.Task;
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
    }

    @Autowired
    private EntityManager entityManager;

    public Task findById(UUID id) {
        return (Task) entityManager.createNativeQuery("select task.* from task" +
                "    where id = :id", Task.class)
                .setParameter(QueryParameters.ID, id.toString()).getSingleResult();
    }

    @Transactional
    public void create(Task task, UUID journalId) {
        entityManager.persist(task);
        entityManager.createNativeQuery("INSERT into journal_tasks_mapping " +
                "values(:journal_id,:task_id)")
                .setParameter(QueryParameters.TASK_JOURNAL_ID, journalId.toString())
                .setParameter(QueryParameters.TASK_ID, task.getId().toString()).executeUpdate();
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


    public List getTasksByJournalId(UUID id) {
        return entityManager.createNativeQuery("select task.* from task\n" +
                "    inner join\n" +
                "    (select * from journal_tasks_mapping\n" +
                "    where journal_id = :id)\n" +
                "        as Journal\n" +
                "        on task_id =id; ", Task.class)
                .setParameter(QueryParameters.ID, id.toString()).getResultList();
    }

    public Task getTaskByJournalIdAndTaskId(UUID taskId, UUID journalId) {
        return (Task) entityManager.createNativeQuery("select task.* from task\n" +
                "    inner join\n" +
                "    (select * from journal_tasks_mapping\n" +
                "    where journal_id = :journalId)\n" +
                "        as Journal\n" +
                "        on task_id =id where task_id = :taskId", Task.class)
                .setParameter(QueryParameters.TASK_JOURNAL_ID, journalId.toString())
                .setParameter(QueryParameters.TASK_ID, taskId.toString()).getSingleResult();
    }


//    public List<Task> getTasksByJournalId(UUID id) {
//       // return entityManager.createNativeQuery("SELECT * FROM Task WHERE tasks_journal_id = :id").setParameter("id", id).getResultList();
//        return entityManager.createNativeQuery("SELECT t.* FROM Task t " +
//                " inner join journal_tasks_mapping jtm on jtm.task_id = t.id " +
//                "WHERE jtm.journal_id = :id ", Task.class).setParameter("id", id.toString() ).getResultList();
//    }
}