package com.company;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonSerializer implements Serializer {


    public Object serializeBeauty(TasksJournal o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        return json; //JSON
    }

    public Object serialize(TasksJournal o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(o);
        return json; //JSON
    }

    public TasksJournal deserialize(Object o) throws JsonProcessingException {
        if(!(o instanceof String))
            return null; //todo чаще всего возвращать null - это плохая практика, т.к. порождает возможность получить NullPointerException
        String json = (String) o;
        ObjectMapper objectMapper = new ObjectMapper();
        TasksJournal t = objectMapper.readValue(json, TasksJournal.class); //todo стоит навсегда забыть переменные с неговорящими именами.
        // Они допустимы только для тестового кода, котрый запустится один раз и потом будет удален.
        // Весь код, котрый попадает под систему контроля версий, должен содержать только говорящие имена переменных.
        return t;

    }
}
