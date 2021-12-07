package com.company.serializer.impl;

import com.company.exception.InvalidTypeClassException;
import com.company.model.TasksJournal;
import com.company.serializer.Serializer;

import java.io.*;

public class BytesSerializer implements Serializer {

    @Override
    public Object serialize(TasksJournal o) throws InvalidTypeClassException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(o);
            } catch (IOException e) {
                //todo при выбрасывании нового эксепшена всегда нужно передать в конструктор оригинальный эксепшен, чтобы не терялся стек вызовов. Иначе возникшие проблемы очень сложно анализировать.
                throw new InvalidTypeClassException("Error during byte array serialization");
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new InvalidTypeClassException("Error during byte array creation");
        }
    }

    @Override
    public TasksJournal deserialize(Object o) throws InvalidTypeClassException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])o)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                return (TasksJournal) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new InvalidTypeClassException("Error during byte array deserialization");
            }
        } catch (IOException e) {
            throw new InvalidTypeClassException("Error during byte array creation");
        }
    }
}