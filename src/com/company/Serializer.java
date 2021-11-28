package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Serializer {


    Object serialize(TasksJournal o) throws JsonProcessingException; // представление объекта в каком-то формате byte and json

    TasksJournal deserialize(Object o) throws JsonProcessingException; // o либо Json либо byte



}
