package com.company.serializer.impl;

import com.company.exception.SerializationException;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;

import java.io.*;

public class BytesSerializer implements Serializer {

    @Override
    public Object serialize(TasksJournal o) throws SerializationException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(o);
            } catch (IOException e) {
                //todo при выбрасывании нового эксепшена всегда нужно передать в конструктор оригинальный эксепшен, чтобы не терялся стек вызовов. Иначе возникшие проблемы очень сложно анализировать.
                throw new SerializationException("Cannot serialize object! Failed to write object to stream", e);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Cannot serialize object! Failed to get byte array from stream",e);
        }
    }

    @Override
    public TasksJournal deserialize(Object o) throws SerializationException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])o)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                return (TasksJournal) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new SerializationException("Cannot deserialize object! Failed to deserialize byte array ",e);
            }
        } catch (IOException e) {
            throw new SerializationException("Cannot deserialize object! Failed to create byte array",e);
        }
    }
}