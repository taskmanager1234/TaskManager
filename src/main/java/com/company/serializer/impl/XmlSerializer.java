package com.company.serializer.impl;

import com.company.exception.SerializationException;
import com.company.serializer.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlSerializer<T> implements Serializer<T> {

    @Override
    public Object serialize(T o) throws SerializationException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize object! Failed to get XML from Java object.", e);
        }
    }


    @Override
    public T deserialize(Object o, Class<T> clazz) throws SerializationException {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue((String) o, clazz);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot deserialize object! Failed to convert XML to Java object using XmlMapper class", e);
        }
    }

}
