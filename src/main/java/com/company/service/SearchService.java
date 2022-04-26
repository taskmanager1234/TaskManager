package com.company.service;

import com.company.exception.BadCriterionException;
import com.company.model.Task;
import com.company.repository.TaskRepository;
import com.company.service.utils.Condition;
import com.company.service.utils.Field;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

        //TODO* цель - формирование SQL-condition отвечал один класс
        public String getPreparedValue(String value) {
            if (Condition.CONTAINS.equals(condition) || Condition.NOT_CONTAINS.equals(condition))
                value = "%" + value + "%";
            if (condition.equals(Condition.STARTS_WITH))
                value = value + "%";
            return value;
        }

        public String getCriterionString() throws BadCriterionException {

            String field = "";
            Map<Field, String> fieldMapping = Field.getMapping();
            if (fieldMapping.containsKey(this.field))
                field = fieldMapping.get(this.field);
            if (field.isEmpty())
                throw new BadCriterionException("Field criterion is absent");

            String condition = "";
            Map<Condition, String> conditionMapping = Condition.getMapping();
            if (conditionMapping.containsKey(this.condition))
                condition = conditionMapping.get(this.condition);
            if (condition.isEmpty())
                throw new BadCriterionException("Condition criterion is absent");

            return String.format("task.%s %s :value and task.journal_id = :journal_id", field, condition);
        }


    }


    public List<Task> searchTasksByCriterion(Criterion criterion, String value, UUID journalId) throws BadCriterionException {
        return taskRepository.getTasksByCondition(criterion, value, journalId);
    }


}
