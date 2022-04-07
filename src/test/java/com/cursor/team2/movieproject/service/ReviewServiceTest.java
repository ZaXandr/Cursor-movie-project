package com.cursor.team2.movieproject.service;

import com.cursor.team2.movieproject.entity.Category;
import com.cursor.team2.movieproject.entity.Movie;
import com.cursor.team2.movieproject.entity.Rate;
import com.cursor.team2.movieproject.entity.Review;
import com.cursor.team2.movieproject.exception.MovieNotFoundException;
import com.cursor.team2.movieproject.exception.ReviewAlreadyExistsException;
import com.cursor.team2.movieproject.exception.ReviewNotFoundException;
import com.cursor.team2.movieproject.repository.MovieRepository;
import com.cursor.team2.movieproject.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private MovieRepository movieRepository;

    private ReviewService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ReviewService(reviewRepository, movieRepository);
    }

    @Test
    void whenAddReview_ShouldReturnReview() {
        Movie movie = new Movie(1, "dsada", Category.DRA, "sdad", "das", new Rate());
        Review review = new Review(1L, "sad", true, movie);

        when(reviewRepository.existsById(review.getId())).thenReturn(false);
        underTest.addReview(review);

        ArgumentCaptor<Review> reviewArgumentCaptor =
                ArgumentCaptor.forClass(Review.class);

        verify(reviewRepository).save(reviewArgumentCaptor.capture());

        Review capturedReview = reviewArgumentCaptor.getValue();
        assertThat(capturedReview).isEqualTo(review);
    }

    @Test
    void whenAddReview_ShouldReturnReviewAlreadyExistException() {

        Review review = new Review()
                .setId(1L)
                .setReview("asd");

        when(reviewRepository.existsById(review.getId())).thenReturn(true);

        Assertions.assertThrows(ReviewAlreadyExistsException.class,() -> underTest.addReview(review));
    }

    @Test
    void whenAddReview_ShouldReturnMovieNotFoundException() {
        Review review = new Review()
                .setId(1)
                .setReview("test")
                .setLiked(true)
                .setMovie(new Movie().setId(1));

        when(movieRepository.existsById(1l)).thenReturn(false);

        Assertions.assertThrows(MovieNotFoundException.class,() -> underTest.addReview(review));
    }

    @Test
    void whenGetById_ShouldReturnReviewWithId() {
        Review review = new Review();
        review.setId(1L);

        when(reviewRepository.findById(review.getId()))
                .thenReturn(Optional.of(review));



        Review expected = underTest.getById(review.getId());
        assertThat(expected).isSameAs(review);
        verify(reviewRepository).findById(review.getId());

    }

    @Test
    void whenGetById_ShouldThrowException() {
        Review review = new Review();
        review.setId(1L);

        when(reviewRepository.findById(review.getId()))
                .thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(ReviewNotFoundException.class, () -> underTest.getById(review.getId()));
    }

    @Test
    void deleteReview() {
        Review review = new Review()
                .setId(1L)
                .setReview("test review");

        when(reviewRepository.findById(review.getId()))
                .thenReturn(Optional.of(review));

        underTest.deleteReview(review.getId());
        verify(reviewRepository).deleteById(review.getId());
    }

    @Test
    void whenDeleteReview_ShouldThrowException() {
        Review review = new Review()
                .setId(1L)
                .setReview("test review");

        when(reviewRepository.findById(review.getId()))
                .thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(ReviewNotFoundException.class, () -> underTest.deleteReview(review.getId()));
    }

    @Test
    void whenUpdateReview_ShouldReturnUpdatedReview() {
        Review review = new Review()
                .setReview("test review")
                .setId(1L);

        Review newReview = new Review()
                .setId(1L)
                .setReview("new review");

        when(reviewRepository.existsById(review.getId())).thenReturn(true);

        underTest.updateReview(newReview);

        ArgumentCaptor<Review> reviewArgumentCaptor =
                ArgumentCaptor.forClass(Review.class);

        verify(reviewRepository).save(reviewArgumentCaptor.capture());

        Review capturedReview = reviewArgumentCaptor.getValue();
        assertThat(capturedReview).isEqualTo(newReview);

    }

    @Test
    void whenUpdateReview_ShouldThrowException() {
        Review review = new Review()
                .setReview("test review")
                .setId(1L);

        Review newReview = new Review()
                .setId(1L)
                .setReview("new review");

        when(reviewRepository.existsById(review.getId())).thenReturn(false);
        Assertions.assertThrows(ReviewNotFoundException.class, () -> underTest.updateReview(newReview));
    }
}