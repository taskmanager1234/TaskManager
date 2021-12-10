package com.company.serializer;


import com.company.exception.SerializationException;
import com.company.model.TasksJournal;

public interface Serializer {

    //todo Если Serializer - это общий интерфейс для любой сериализации, то почему в сигнатуре метода присутствует JsonProcessingException - специфический эксепшен, который может возникать только при работе с json
    Object serialize(TasksJournal o) throws SerializationException; // представление объекта в каком-то формате byte or json

    TasksJournal deserialize(Object o) throws SerializationException; // o либо Json либо byte



}
