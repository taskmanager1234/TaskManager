package com.company.serializer.impl;

import com.company.exception.SerializationException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.List;

public class XmlSerializer implements Serializer {

    @Override
    public Object serialize(TasksJournal o) throws SerializationException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get XML from Java object.", e);
        }
    }

    public Object serializeTasks(List<Task> tasks) throws SerializationException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(tasks);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get XML from Java object.", e);
        }
    }


    @Override
    public TasksJournal deserialize(Object o) throws SerializationException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue((String) o, TasksJournal.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert XML to Java object using XmlMapper class", e);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Task> deserializeTasks(Object o) throws SerializationException {
        if (!(o instanceof String))
            throw new SerializationException("Cannot deserialize object! Required object type is String, but actual is " + o.getClass().getName());
        String xml = (String) o;
        XmlMapper xmlMapper = new XmlMapper();
        List<Task> tasks;

        try {
            tasks = xmlMapper.readValue(xml, new TypeReference<List<Task>>() {});
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert XML string to Java object using ObjectMapper class", e);
        }

        return tasks;

    }
}
