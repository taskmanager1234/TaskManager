package com.company.repository;

import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.model.User;
import lombok.SneakyThrows;
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
        public static final String ID = "id";
        public static final String TASK_JOURNAL_ID = "journal_id";
        public static final String USER_ID = "user_id";
    }

    public TasksJournal findById(UUID id) {
        return entityManager.find(TasksJournal.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<TasksJournal> getJournalsByUserId(UUID userId) {
        String query = String.format("select * from task_journal where user_id = :%s",
                QueryParameters.USER_ID);
        return (List<TasksJournal>) entityManager.createNativeQuery(query, TasksJournal.class)
                .setParameter(QueryParameters.USER_ID, userId.toString()).getResultList();
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
