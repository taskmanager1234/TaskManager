package com.company.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Field {
    TITLE("Title"),
    DESCRIPTION("Description");

    private final String field;

    Field(String field) {
        this.field = field;
    }

    public String getNameField() {
        return this.field;
    }

    public static List<String> stringValues() {
        List<String> fields = new ArrayList<>();
        for (Field field : Field.values()) {
            fields.add(field.getNameField());
        }
        return fields;
    }

    public static Map<Field, String> getMapping() {
        Map<Field, String> result = new HashMap<>();
        result.put(TITLE, "title");
        result.put(DESCRIPTION, "description");

        return result;
    }
}
