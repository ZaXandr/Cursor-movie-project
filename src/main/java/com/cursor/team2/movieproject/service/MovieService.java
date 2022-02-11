package com.cursor.team2.movieproject.service;

import com.cursor.team2.movieproject.dto.MovieDto;
import com.cursor.team2.movieproject.dto.MoviePageDto;
import com.cursor.team2.movieproject.dto.ReviewDto;
import com.cursor.team2.movieproject.entity.Category;
import com.cursor.team2.movieproject.entity.Movie;
import com.cursor.team2.movieproject.entity.Rate;
import com.cursor.team2.movieproject.mapper.MovieMapper;
import com.cursor.team2.movieproject.mapper.ReviewMapper;
import com.cursor.team2.movieproject.repository.MovieRepository;
import com.cursor.team2.movieproject.repository.RateRepository;
import com.cursor.team2.movieproject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    ReviewMapper reviewMapper;


    public List<MovieDto> getAllMovie() {
        return movieRepository.findAll().stream().map(movie -> {return movieMapper.toDto(movie);}).collect(Collectors.toList());
    }

    public MoviePageDto getMovieById(long id) {
        MoviePageDto movie = new MoviePageDto();
        movie = movieMapper.toPageDto(movieRepository.getById(id)).setReviews(reviewRepository.getByMovieId(id)
                .stream()
                .map(review -> {
                    ReviewDto reviewDto = reviewMapper.toDto(review);
                    return reviewDto;
                })
                .collect(Collectors.toList()));

        return movie;
    }

    public Movie addMovie(Movie movie) {
        Optional<Movie> movieById = movieRepository.findById(movie.getId());
        if (movieById.isEmpty()) {
            return movieRepository.save(movie);
        } else {
//            throw new MovieAlreadyExistsException();
            return null;
        }
    }

    public Movie updateMovie(Movie movie) {
        Optional<Movie> movieById = movieRepository.findById(movie.getId());
        if (movieById.isEmpty()) {
//            throw new NotFoundMovieException(message);
            return null;
        } else {
            return movieRepository.save(movie);
        }
    }

    public void deleteMovie(long id) {
        Movie movie = movieRepository.getById(id);
        if (Objects.isNull(movie)) {
// throw new NotFoundMovieException(message);
        } else {
            movieRepository.delete(movie);
        }
    }

    public Rate updateRating(int value) {
        Movie movie = movieRepository.getById(1);
        Rate rate = movie.getRate();
        rate.setAverage((movie.getRate().getAverage() * movie.getRate().getVoices() + value) / (movie.getRate().getVoices()+1));
        rate.setVoices(rate.getVoices()+1);
        return rateRepository.save(rate);

    }

    public List<MovieDto> getMovieByCategory(String category) {
        Category categoryForSearch = Category.testByKey(category);
        return movieRepository.findByCategory(categoryForSearch).stream().
                map(movie ->
                {MovieDto movieDto = movieMapper.toDto(movie);
                    return movieDto;})
                .collect(Collectors.toList());
    }

    public List<MovieDto> getMovieByRating() {
        return movieRepository.getMovieByRating().stream()
                .map(movie -> {
                    MovieDto movieDto = movieMapper.toDto(movie);
                    return movieDto;
                })
                .collect(Collectors.toList());
    }
}
