package com.company.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

//todo оригинальная проблема этим эксепшеном не решена - этот эксепшен все равно представляет собой ошибка парсинга JSON. А интерфейс Serializer - общий для любой сериализации, а не только json-сериализации.
public class InvalidTypeClassException extends Exception {

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public InvalidTypeClassException(String s) {
        super(s);
    }
}
