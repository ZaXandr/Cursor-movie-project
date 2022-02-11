package com.cursor.team2.movieproject.controller;

import com.cursor.team2.movieproject.dto.MovieDto;
import com.cursor.team2.movieproject.dto.MoviePageDto;
import com.cursor.team2.movieproject.entity.Movie;
import com.cursor.team2.movieproject.entity.Rate;
import com.cursor.team2.movieproject.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/movie")
    public List<MovieDto> getAllMovie(){
        return movieService.getAllMovie();
    }

    @GetMapping("/movie/id/{id}")
    public MoviePageDto getMovieById(@PathVariable long id){
        return movieService.getMovieById(id);
    }

    @GetMapping("movie/category/{category}")
    public List<MovieDto> getMovieByCategory(@PathVariable String category){
        return movieService.getMovieByCategory(category);
    }

    @GetMapping("movie/rating")
    public List<MovieDto> getMovieByRating(){
        return movieService.getMovieByRating();
    }


    @PostMapping("/movie")
    public Movie addMovie(@RequestBody Movie movie){
        return movieService.addMovie(movie);
    }

    @PutMapping("/movie")
    public Movie updateMovie(@RequestBody Movie movie){
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@PathVariable long id){
        movieService.deleteMovie(id);
    }

    @PutMapping("/movie/rating/{value}")
    public Rate updateRating(@PathVariable int value){
        return movieService.updateRating(value);
    }
}
