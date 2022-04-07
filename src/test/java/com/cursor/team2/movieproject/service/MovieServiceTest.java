package com.cursor.team2.movieproject.service;

import com.cursor.team2.movieproject.dto.MovieDto;
import com.cursor.team2.movieproject.dto.MoviePageDto;
import com.cursor.team2.movieproject.entity.Category;
import com.cursor.team2.movieproject.entity.Movie;
import com.cursor.team2.movieproject.entity.Rate;
import com.cursor.team2.movieproject.exception.MovieAlreadyExistsException;
import com.cursor.team2.movieproject.exception.MovieNotFoundException;
import com.cursor.team2.movieproject.exception.WrongCategoryException;
import com.cursor.team2.movieproject.exception.WrongRatingException;
import com.cursor.team2.movieproject.mapper.MovieMapper;
import com.cursor.team2.movieproject.mapper.ReviewMapper;
import com.cursor.team2.movieproject.repository.MovieRepository;
import com.cursor.team2.movieproject.repository.RateRepository;
import com.cursor.team2.movieproject.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieServiceTest {


    @Mock
    MovieRepository movieRepository;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    RateRepository rateRepository;
    @Mock
    MovieMapper movieMapper;
    @Mock
    ReviewMapper reviewMapper;

    private MovieService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MovieService(movieRepository,
                reviewRepository, rateRepository, movieMapper, reviewMapper);
    }

    @Test
    void getAllMovie() {

        List<Movie> movies = List.of(new Movie().setId(1L).setName("lala"),
                new Movie().setId(2L).setName("karkar"));

        List<MovieDto> given = movies.stream().map(movieMapper::toDto).collect(Collectors.toList());

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDto> expected = underTest.getAllMovie();
        assertEquals(given, expected);

        verify(movieRepository).findAll();

    }

    @Test
    void WhenGetMovieById_ShouldReturnMovie() {
        long id = 1L;
        Movie movie = new Movie()
                .setId(id)
                .setName("Karlson")
                .setCategory(Category.COM);
        MoviePageDto moviePageDto = new MoviePageDto()
                .setId(id)
                .setName("Karlson")
                .setCategory("Comedy");

        when(movieMapper.toPageDto(movie)).thenReturn(moviePageDto);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(reviewRepository.geByMovieId(id)).thenReturn(new ArrayList<>());

        MoviePageDto expected = underTest.getMovieById(id);

        assertThat(expected).isSameAs(movieMapper.toPageDto(movie));

    }


    @Test
    void WhenGetMovieById_ShouldReturnException() {

        when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(MovieNotFoundException.class,
                () -> underTest.getMovieById(1L));
        verify(movieRepository, never()).getById(1L);
    }

    @Test
    void WhenAddMovie_ShouldReturnNewMovie() {
        Movie movie = new Movie()
                .setId(1l)
                .setName("test")
                .setCategory(Category.COM)
                .setDescription("good")
                .setDirector("me")
                .setRate(new Rate());

        when(movieRepository.existsById(1l)).thenReturn(false);

        underTest.addMovie(movie);

        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository).save(captor.capture());
        Movie capturedMovie = captor.getValue();
        assertThat(capturedMovie).isSameAs(movie);
    }

    @Test
    void WhenAddMovie_ShouldThrowException() {
        Movie movie = new Movie()
                .setId(1l)
                .setName("test")
                .setCategory(Category.COM)
                .setDescription("good")
                .setDirector("me")
                .setRate(new Rate());

        when(movieRepository.existsById(1l)).thenReturn(true);
        Assertions.assertThrows(MovieAlreadyExistsException.class,
                () -> underTest.addMovie(movie));
        verify(movieRepository, never()).save(movie);
    }

    @Test
    void WhenUpdateMovie_ShouldReturnUpdatedMovie() {
        Movie newMovie = new Movie()
                .setId(1l)
                .setName("newTest")
                .setCategory(Category.COM)
                .setDescription("bad")
                .setDirector("me")
                .setRate(new Rate());

        when(movieRepository.existsById(1l)).thenReturn(true);

        underTest.updateMovie(newMovie);

        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository).save(captor.capture());
        Movie capturedMovie = captor.getValue();
        assertThat(capturedMovie).isSameAs(newMovie);
        verify(movieRepository).save(newMovie);
    }

    @Test
    void WhenUpdateMovie_ShouldReturnException() {
        Movie movie = new Movie()
                .setId(1l)
                .setName("las");

        when(movieRepository.existsById(1l)).thenReturn(false);

        Assertions.assertThrows(MovieNotFoundException.class,
                () -> underTest.updateMovie(movie));
        verify(movieRepository, never()).save(movie);
        verify(movieRepository, atMostOnce()).existsById(movie.getId());
    }

    @Test
    void WhenDeleteMovie_ShouldDeleteMovie() {
        Movie movie = new Movie()
                .setId(1l)
                .setName("test");

        when(movieRepository.existsById(1l)).thenReturn(true);

        underTest.deleteMovie(movie.getId());

        verify(movieRepository, atMostOnce()).deleteById(movie.getId());
    }

    @Test
    void WhenDeleteMovie_ShouldThrowException() {
        Movie movie = new Movie()
                .setId(1l)
                .setName("test");

        when(movieRepository.existsById(1l)).thenReturn(false);

        Assertions.assertThrows(MovieNotFoundException.class,
                () -> underTest.deleteMovie(movie.getId()));
        verify(movieRepository, never()).deleteById(movie.getId());
    }

    @Test
    void WhenUpdateRating_ShouldReturnNEwRating() {
        Long id = 1l;
        Rate rate = new Rate()
                .setId(1l)
                .setAverage(2)
                .setVoices(1);

        Movie movie = new Movie()
                .setId(id)
                .setRate(rate);

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        Rate expected = underTest.updateRating(id, 10);

        Assertions.assertEquals(expected.getAverage(),6);
        Assertions.assertEquals(expected.getVoices(),3);
    }

    @Test
    void WhenUpdateRating_ShouldThrowWrongRateException() {
        Long id = 1l;

        when(movieRepository.findById(id)).thenReturn(Optional.of(new Movie()));

        Assertions.assertThrows(WrongRatingException.class,
                () -> underTest.updateRating(id, 11));
    }

    @Test
    void WhenUpdateRating_ShouldThrowException() {
        when(movieRepository.findById(1l)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(MovieNotFoundException.class,
                () -> underTest.updateRating(1L, 10));
    }

    @Test
    void getMovieByCategory_ShouldReturnListOfMovieDtos() {
        String category = "Action";
        List<Movie> movies = List.of(
                new Movie().setId(1).setName("test1").setCategory(Category.ACT),
                new Movie().setId(2).setName("test2").setCategory(Category.ACT)
        );
        when(movieRepository.findByCategory(Category.testByKey(category))).thenReturn(movies);

        List<MovieDto> actual = movies.stream().map(movieMapper::toDto).collect(Collectors.toList());

        List<MovieDto> expected = underTest.getMovieByCategory(category);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getMovieByCategory_ShouldReturnException() {
        String category = "invalid";

        when(movieRepository.findByCategory(Category.testByKey(category))).thenReturn(null);

        Assertions.assertThrows(WrongCategoryException.class,
                () -> underTest.getMovieByCategory(category));
        verify(movieRepository, never()).findByCategory(Category.testByKey(category));
    }

    @Test
    void getMovieByRatingDesc() {
        List<Movie> movies = List.of(new Movie().setId(1).setRate(new Rate().setId(1).setAverage(10)),
                new Movie().setId(2).setRate(new Rate().setId(1).setAverage(9)),
                new Movie().setId(3).setRate(new Rate().setId(3).setAverage(8)));

        when(movieRepository.getMovieByRatingDesc())
                .thenReturn(movies);
        List<MovieDto> expected = underTest.getMovieByRatingDesc();
        Assertions.assertEquals(expected, movies.stream().map(movieMapper::toDto).collect(Collectors.toList()));
        verify(movieRepository, atMostOnce()).findByCategory(Category.ACT);
    }

    @Test
    void getMovieByRatingAsc() {
        List<Movie> movies = List.of(new Movie().setId(1).setRate(new Rate().setId(1).setAverage(8)),
                new Movie().setId(2).setRate(new Rate().setId(1).setAverage(9)),
                new Movie().setId(3).setRate(new Rate().setId(3).setAverage(10)));

        when(movieRepository.getMovieByRatingAsc())
                .thenReturn(movies);
        List<MovieDto> expected = underTest.getMovieByRatingAsc();
        Assertions.assertEquals(movies.stream().map(movieMapper::toDto).collect(Collectors.toList()),
                expected);
    }
}