package com.cursor.team2.movieproject.mapper;


import com.cursor.team2.movieproject.dto.MovieDto;
import com.cursor.team2.movieproject.dto.MoviePageDto;
import com.cursor.team2.movieproject.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDto toDto(Movie movie) {
        MovieDto movieDto = new MovieDto().setId(movie.getId())
                .setName(movie.getName())
                .setCategory(movie.getCategory().getName())
                .setDirector(movie.getDirector())
                .setDescription(movie.getDescription());
        return movieDto;
    }

    public MoviePageDto toPageDto(Movie movie) {
        MoviePageDto movieDto = new MoviePageDto().setId(movie.getId())
                .setName(movie.getName())
                .setCategory(movie.getCategory().getName())
                .setDirector(movie.getDirector())
                .setDescription(movie.getDescription());
        return movieDto;
    }
}