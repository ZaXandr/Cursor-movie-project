package com.cursor.team2.movieproject.exception;

public class MovieAlreadyExistsException extends RuntimeException {
    private long id;

    public MovieAlreadyExistsException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Movie with: " + id + " Already exists";
    }
}
