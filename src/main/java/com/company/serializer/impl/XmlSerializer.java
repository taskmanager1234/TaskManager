package com.company.serializer.impl;

import com.company.exception.SerializationException;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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


    @Override
    public TasksJournal deserialize(Object o) throws SerializationException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue((String)o, TasksJournal.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert XML to Java object using XmlMapper class", e);
        }
    }
}
