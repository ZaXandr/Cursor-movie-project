package com.cursor.team2.movieproject.entity;

import java.util.*;
import java.util.stream.Collectors;

public enum Category {
    ACT("Action"),
    COM("Comedy"),
    DRA("Drama"),
    FAN("Fantasy"),
    HOR("Horror"),
    MYS("Mystery"),
    ROM("Romance"),
    THR("Thriller"),
    WES("Western"),
    SCI_FI("Science_fiction");


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

    public static List<String> getNames() {
       return Arrays.stream(Category.values()).map(category -> {return category.name;}).collect(Collectors.toList());
    }


    public static Category testByKey(String category) {
        return map.get(category);
    }

}