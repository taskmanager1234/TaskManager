package com.company.serializer.impl;


import com.company.exception.SerializationException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UncheckedIOException;
import java.util.List;

public class JsonSerializer implements Serializer {


    public Object serializeBeauty(TasksJournal o) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize(Beauty) object! Failed to get JSON string from Java object. ", e);
        }
        return json;
    }

    public Object serialize(TasksJournal o) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get JSON string from Java object.", e);
        }
        return json;
    }

    public Object serializeTasks(List<Task> tasks) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(tasks);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get JSON string from Java object.", e);
        }
        return json;
    }

    public TasksJournal deserialize(Object o) throws SerializationException {
        if (!(o instanceof String))
            throw new SerializationException("Cannot deserialize object! Required object type is String, but actual is " + o.getClass().getName());
        String json = (String) o;
        ObjectMapper objectMapper = new ObjectMapper();
        TasksJournal tasksJournal = null;
        try {
            tasksJournal = objectMapper.readValue(json, TasksJournal.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert JSON string to Java object using ObjectMapper class", e);
        }
        return tasksJournal;

    }

    @SuppressWarnings("unchecked")
    public List<Task> deserializeTasks(Object o) throws SerializationException {
        if (!(o instanceof String))
            throw new SerializationException("Cannot deserialize object! Required object type is String, but actual is " + o.getClass().getName());
        String json = (String) o;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Task> tasks;
        try {
            tasks = objectMapper.readValue(json, new TypeReference<List<Task>>() {
            });
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert JSON string to Java object using ObjectMapper class", e);
        }
        return tasks;

    }
}
