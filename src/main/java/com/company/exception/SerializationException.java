package com.company.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

//todo оригинальная проблема этим эксепшеном не решена - этот эксепшен все равно представляет собой ошибка парсинга JSON. А интерфейс Serializer - общий для любой сериализации, а не только json-сериализации.
public class SerializationException extends Exception {

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public SerializationException(String s) {
        super(s);
    }
}
