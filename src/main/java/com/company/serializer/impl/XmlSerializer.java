package com.company.serializer.impl;

import com.company.exception.InvalidTypeClassException;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlSerializer implements Serializer {

    @Override
    public Object serialize(TasksJournal o) throws InvalidTypeClassException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new InvalidTypeClassException("Ser XML");
        }
    }


    @Override
    public TasksJournal deserialize(Object o) throws InvalidTypeClassException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue((String)o, TasksJournal.class);
        } catch (JsonProcessingException e) {
            throw new InvalidTypeClassException("Des XML");
        }
    }
}
