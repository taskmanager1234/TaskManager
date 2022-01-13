package com.company.serializer;


import com.company.exception.SerializationException;
import com.company.model.TasksJournal;

public interface Serializer {

    Object serialize(TasksJournal o) throws SerializationException; // представление объекта в каком-то формате byte or json

    TasksJournal deserialize(Object o) throws SerializationException; // o либо Json либо byte



}
