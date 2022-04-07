package com.cursor.team2.movieproject.repository;

import com.cursor.team2.movieproject.entity.Category;
import com.cursor.team2.movieproject.entity.Movie;
import com.cursor.team2.movieproject.entity.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    RateRepository rateRepository;

    @Autowired
    private MovieRepository underTest;

    @BeforeEach
    void setUp() {
        Movie test1 = new Movie()
                .setCategory(Category.ACT);
        Movie test2 = new Movie()
                .setCategory(Category.COM);
        Movie test3 = new Movie()
                .setCategory(Category.DRA);
        underTest.save(test1);
        underTest.save(test2);
        underTest.save(test3);
    }

    @Test
    void findByCategory() {

        List<Movie> movies = underTest.findByCategory(Category.COM);

        List<Movie> expected = underTest.findAll().stream()
                .filter(movie -> movie.getCategory().equals(Category.COM))
                .collect(Collectors.toList());

        assertThat(movies).isEqualTo(expected);
    }

    @Test
    void getMovieByRatingDesc() {
    }

    @Test
    void getMovieByRatingAsc() {
    }
}