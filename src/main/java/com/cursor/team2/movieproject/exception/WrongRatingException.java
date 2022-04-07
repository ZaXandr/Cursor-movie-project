package com.cursor.team2.movieproject.exception;

public class WrongRatingException extends RuntimeException {
    int rate;

    public WrongRatingException(String message, int rate) {
        super(message);
        this.rate = rate;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + rate + "try in range 1-10";
    }
}
