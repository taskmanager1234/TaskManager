package com.company.serializer.impl;

import com.company.exception.SerializationException;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlSerializer implements Serializer {


    public Object serializeBeauty(TasksJournal o) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize (beauty) object! Failed to get Yaml from Java object.",e);
        }
        return json;
    }

    public Object serialize(TasksJournal o) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get Yaml from Java object.",e);
        }
        return json;
    }

    public TasksJournal deserialize(Object o) throws SerializationException {
        if(!(o instanceof String))
            throw new SerializationException("Cannot deserialize object! Required object type is String, but actual is <type of o>"); //todo чаще всего возвращать null - это плохая практика, т.к. порождает возможность получить NullPointerException
        String json = (String) o;
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        TasksJournal tasksJournal = null; //todo стоит навсегда забыть переменные с неговорящими именами.
        try {
            tasksJournal = objectMapper.readValue(json, TasksJournal.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert Yaml to Java object using ObjectMapper class",e);
        }

        // Они допустимы только для тестового кода, котрый запустится один раз и потом будет удален.
        // Весь код, котрый попадает под систему контроля версий, должен содержать только говорящие имена переменных.
        return tasksJournal;

    }
}
