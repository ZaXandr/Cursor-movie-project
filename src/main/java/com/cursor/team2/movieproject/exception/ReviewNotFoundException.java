package com.cursor.team2.movieproject.exception;

public class ReviewNotFoundException extends RuntimeException {

    private long id;
    public ReviewNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Review with id: " + id + "Not Found!";
    }
}
