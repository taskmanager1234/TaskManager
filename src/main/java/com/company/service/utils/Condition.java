package com.company.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Condition {
    EQUALS("Equals"),
    CONTAINS("Contains"),
    NOT_CONTAINS("Not contains"),
    STARTS_WITH("Starts with");

    private final String condition;//TODO name
    //TODO добавить поле для condition("=", "LIKE'....)

    Condition(String condition) {
        this.condition = condition;
    }

    public String getNameCondition() {
        return this.condition;
    }

    public static List<String> stringValues() {
        List<String> conditions = new ArrayList<>();
        for (Condition condition : Condition.values()) {
            conditions.add(condition.getNameCondition());
        }
        return conditions;
    }
//TODO
    public static Map<Condition, String> getMapping() {
        Map<Condition, String> result = new HashMap<>();
        result.put(EQUALS, "=");
        result.put(NOT_CONTAINS, "NOT LIKE");
        result.put(CONTAINS, "LIKE");
        result.put(STARTS_WITH, "LIKE");
        return result;
    }

    public static Condition getByStringValue(String value) {
        for (Condition condition : Condition.values()) {
            if (condition.getNameCondition().toUpperCase().equals(value.toUpperCase()))
                return condition;
        }
        return null;
    }

}
