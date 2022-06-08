package com.company.serializer.impl;


import com.company.exception.SerializationException;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer<T> implements Serializer<T> {

    public Object serialize(T o) throws SerializationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get JSON string from Java object.", e);
        }
        return json;
    }


    public T deserialize(Object o, Class<T> clazz) throws SerializationException {
        if (!(o instanceof String))
            throw new SerializationException("Cannot deserialize object! Required object type is String, but actual is " + o.getClass().getName());
        String json = (String) o;
        ObjectMapper objectMapper = new ObjectMapper();
        T tasksJournal;
        try {
            tasksJournal = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert JSON string to Java object using ObjectMapper class", e);
        }
        return tasksJournal;

    }

}
