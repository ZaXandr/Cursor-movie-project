package com.cursor.team2.movieproject.exception;

public class ReviewAlreadyExistsException extends RuntimeException {
    private long id;

    public ReviewAlreadyExistsException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Review with id: " + id + " already exists";
    }
}
