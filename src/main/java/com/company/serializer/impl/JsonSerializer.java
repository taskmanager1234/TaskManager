package com.company.serializer.impl;



import com.company.exception.SerializationException;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer implements Serializer {


    public Object serializeBeauty(TasksJournal o) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize(Beauty) object! Failed to get JSON string from Java object. ",e);
            //todo это что?
        }
        return json;
    }

    public Object serialize(TasksJournal o) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get JSON string from Java object." , e);
        }
        return json;
    }

    public TasksJournal deserialize(Object o) throws SerializationException {
        if(!(o instanceof String))
            //todo error message должен содержать контекстную информацию о том, что именно пошло не так, чтобы прочитавший его мог понять, в чем именно проблема.
            //сравни эти два сообщения "Invalid type class" и "Cannot deserialize object! Required object type is String, but actual is <type of o>"
            //это относится ко всем эксепшенам в системе
            throw new SerializationException("Cannot deserialize object! Required object type is String, but actual is <type of o>");
        String json = (String) o;
        ObjectMapper objectMapper = new ObjectMapper();
        TasksJournal tasksJournal = null;
        try {
            tasksJournal = objectMapper.readValue(json, TasksJournal.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert JSON string to Java object using ObjectMapper class",e);
        }
        return tasksJournal;

    }
}
