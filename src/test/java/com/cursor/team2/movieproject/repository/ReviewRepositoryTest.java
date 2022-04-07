package com.cursor.team2.movieproject.repository;

import com.cursor.team2.movieproject.entity.Movie;
import com.cursor.team2.movieproject.entity.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void geByMovieId() {
        Movie movie = new Movie().setId(1l);
        movieRepository.save(movie);

        Review review = new Review()
                .setId(1l)
                .setReview("good")
                .setLiked(true)
                .setMovie(movie);

        Review review2 = new Review()
                .setId(1l)
                .setReview("good")
                .setLiked(true);

        underTest.save(review);
        underTest.save(review2);

        List<Review> reviews = underTest.geByMovieId(1);

        assertThat(reviews.size()).isEqualTo(1);

    }
}