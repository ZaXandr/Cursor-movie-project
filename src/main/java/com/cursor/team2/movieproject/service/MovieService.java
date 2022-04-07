package com.cursor.team2.movieproject.service;

import com.cursor.team2.movieproject.dto.MovieDto;
import com.cursor.team2.movieproject.dto.MoviePageDto;
import com.cursor.team2.movieproject.entity.Category;
import com.cursor.team2.movieproject.entity.Movie;
import com.cursor.team2.movieproject.entity.Rate;
import com.cursor.team2.movieproject.exception.*;
import com.cursor.team2.movieproject.mapper.MovieMapper;
import com.cursor.team2.movieproject.mapper.ReviewMapper;
import com.cursor.team2.movieproject.repository.MovieRepository;
import com.cursor.team2.movieproject.repository.RateRepository;
import com.cursor.team2.movieproject.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieService {


    private final MovieRepository movieRepository;

    private final ReviewRepository reviewRepository;

    private final RateRepository rateRepository;

    private final MovieMapper movieMapper;

    private final ReviewMapper reviewMapper;

    public MovieService(MovieRepository movieRepository, ReviewRepository reviewRepository, RateRepository rateRepository, MovieMapper movieMapper, ReviewMapper reviewMapper) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.rateRepository = rateRepository;
        this.movieMapper = movieMapper;
        this.reviewMapper = reviewMapper;
    }


    public List<MovieDto> getAllMovie() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    public MoviePageDto getMovieById(long id) {
        return movieRepository.findById(id)
                .map(movieMapper::toPageDto)
                .map(movieDto -> movieDto.setReviews(
                    reviewRepository.geByMovieId(id).stream()
                            .map(reviewMapper::toDto)
                            .collect(Collectors.toList()))
                )
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public Movie addMovie(Movie movie) {
        if (movieRepository.existsById(movie.getId())) {
            throw new MovieAlreadyExistsException(movie.getId());
        } else {
            return movieRepository.save(movie);
        }
    }

    public Movie updateMovie(Movie movie) {
        if (movieRepository.existsById(movie.getId())) {
            return movieRepository.save(movie);
        } else {
            throw new MovieNotFoundException(movie.getId());
        }
    }

    public void deleteMovie(long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else {
            throw new MovieNotFoundException(id);
        }
    }

    public Rate updateRating(Long id, int value) {
        return movieRepository.findById(id)
                .map(movie -> {
                    if (value > 10 || value < 0) {
                        throw new WrongRatingException("unable to put value:", value);
                    } else {
                        Rate rate = movie.getRate();
                        rate.setAverage((movie.getRate().getAverage() * movie.getRate().getVoices() + value)
                                / (movie.getRate().getVoices() + 1));
                        rate.setVoices(rate.getVoices() + 1);
                        return rateRepository.save(rate);
                    }
                })
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public List<MovieDto> getMovieByCategory(String category) {
        Category categoryForSearch = Category.testByKey(category);
        if (Objects.isNull(categoryForSearch)) {
            throw new WrongCategoryException("Wrong category name: ", category);
        } else {
            return movieRepository.findByCategory(categoryForSearch).stream()
                    .map(movieMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    public List<MovieDto> getMovieByRatingDesc() {
        return movieRepository.getMovieByRatingDesc().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<MovieDto> getMovieByRatingAsc() {
        return movieRepository.getMovieByRatingAsc().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }
}
