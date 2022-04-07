package com.company.service;

import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SearchService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager entityManager;


    @Data
    public static class Criterion {
        private final Field field;
        @Nullable
        private final Condition condition;

        public Criterion(Field field, Condition condition) {
            this.field = field;
            this.condition = condition;
        }

        public String getPreparedValue(String value){
            if (condition.equals(Condition.CONTAINS) || condition.equals(Condition.NOT_CONTAINS))
                value = "%" + value + "%";
            if (condition.equals(Condition.STARTS_WITH))
                value = value + "%";
            return value;
        }

        public String getCriterionString() {

            //todo: лучше использовать строки с плейсхолдерами, чем конкатенацию
            return "task." + field.toString() + " " + condition.toString() + " :value and task.journal_id = :journal_id";
        }

        public Field getField() {
            return field;
        }

        public Condition getCondition() {
            return condition;
        }
    }

    public enum Field {
        TITLE("title"),
        DESCRIPTION("description");

        private final String name;

        Field(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        //todo а зачем нужен этот метод?
        public List<String> getValues() {
            List<String> fieldString = new ArrayList<>();

            int count = values().length;
            for (int i = 0; i < count; ++i) {
                fieldString.add(values()[i].toString());
            }
            return fieldString;

        }
    }


    //todo эээ, а почему они дублируются? И почему у них такие разнородные названия?
    public enum Condition {
        EQUALS("="),
        CONTAINS("LIKE"),
        NOT_CONTAINS("NOT LIKE"),
        STARTS_WITH("LIKE");

        private final String name;

        Condition(String name) {
            this.name = name;
        }

        public static List<String> getValues() {
            List<String> conditionString = new ArrayList<>();

            int count = values().length;
            for (int i = 0; i < count; ++i) {
                conditionString.add(values()[i].toString());
            }
            return conditionString;

        }


        @Override
        public String toString() {
            return this.name;
        }


    }


    public List<Task> searchTasksByCriterion(Criterion criterion, String value, UUID journalId) {
        return taskRepository.getTasksByCondition(criterion, value, journalId);
    }


}
