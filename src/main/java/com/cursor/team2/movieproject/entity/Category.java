package com.cursor.team2.movieproject.entity;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    SCI_FI("Science fiction");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static Map<String, Category> map;

    static {
        map = new HashMap<String, Category>();
        for (Category c : Category.values()) {
            map.put(c.name, c);
        }
    }

    public static Category testByKey(String category) {
        return map.get(category);
    }

}