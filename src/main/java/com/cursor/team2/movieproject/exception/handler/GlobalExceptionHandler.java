package com.cursor.team2.movieproject.exception.handler;

import com.cursor.team2.movieproject.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({
            WrongCategoryException.class,
            WrongRatingException.class,
            MovieAlreadyExistsException.class
    })
    public ResponseEntity<Object> wrongInputArgument(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({
            ReviewNotFoundException.class,
            MovieNotFoundException.class
    })
    public ResponseEntity<Object> notFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
