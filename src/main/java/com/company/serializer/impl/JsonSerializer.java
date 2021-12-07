package com.company.serializer.impl;



import com.company.exception.InvalidTypeClassException;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer implements Serializer {


    public Object serializeBeauty(TasksJournal o) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public Object serialize(TasksJournal o) throws InvalidTypeClassException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public TasksJournal deserialize(Object o) throws InvalidTypeClassException {
        if(!(o instanceof String))
            throw new InvalidTypeClassException("Invalid type class"); //todo чаще всего возвращать null - это плохая практика, т.к. порождает возможность получить NullPointerException
        String json = (String) o;
        ObjectMapper objectMapper = new ObjectMapper();
        TasksJournal tasksJournal = null; //todo стоит навсегда забыть переменные с неговорящими именами.
        try {
            tasksJournal = objectMapper.readValue(json, TasksJournal.class);
        } catch (JsonProcessingException e) {
            throw new InvalidTypeClassException("Error json readValue");
        }
        // Они допустимы только для тестового кода, котрый запустится один раз и потом будет удален.
        // Весь код, котрый попадает под систему контроля версий, должен содержать только говорящие имена переменных.
        return tasksJournal;

    }
}
