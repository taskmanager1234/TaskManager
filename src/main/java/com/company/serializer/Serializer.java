package com.company.serializer;


import com.company.exception.SerializationException;

//TODO сделать параметризованным  (T o)
//TODO разные классы для сериализации таски, журнала
public interface Serializer<T> {

    Object serialize(T o) throws SerializationException; // представление объекта в каком-то формате byte or json

    T deserialize(Object o, Class<T> clazz) throws SerializationException; // o либо Json либо byte


}
