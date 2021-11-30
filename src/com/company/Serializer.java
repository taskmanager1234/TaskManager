package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Serializer {

    //todo Если Serializer - это общий интерфейс для любой сериализации, то почему в сигнатуре метода присутствует JsonProcessingException - специфический эксепшен, который может возникать только при работе с json
    Object serialize(TasksJournal o) throws JsonProcessingException; // представление объекта в каком-то формате byte and json

    TasksJournal deserialize(Object o) throws JsonProcessingException; // o либо Json либо byte



}
