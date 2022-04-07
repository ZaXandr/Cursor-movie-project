package com.cursor.team2.movieproject.exception;

import com.cursor.team2.movieproject.entity.Category;

public class WrongCategoryException extends RuntimeException {
    public String name;

    public WrongCategoryException(String message, String name) {
        super(message);
        this.name = name;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + name + " try one of " + Category.getNames();
    }
}
