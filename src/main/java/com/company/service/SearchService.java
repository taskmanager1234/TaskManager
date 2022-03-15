package com.company.service;

import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SearchService {

    @Autowired
    private TaskRepository taskRepository;


    @Data
    public static class Criterion {
        private final Field field;
        private final Condition condition;

        public Criterion(Field field, Condition condition) {
            this.field = field;
            this.condition = condition;
        }

        public String getCriterionString() {
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
    }


    public enum Condition {
        EQUALS("="),
        CONTAINS("LIKE"),
        NOT_CONTAINS("NOT LIKE");

        private final String name;

        Condition(String name) {
            this.name = name;
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
