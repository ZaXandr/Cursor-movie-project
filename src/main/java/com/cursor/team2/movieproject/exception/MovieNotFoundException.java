package com.cursor.team2.movieproject.exception;

public class MovieNotFoundException extends RuntimeException {
    Long id;

    public MovieNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Movie with id: " + id + " NOT FOUND";
    }
}
