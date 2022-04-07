package com.cursor.team2.movieproject.service;

import com.cursor.team2.movieproject.entity.Review;
import com.cursor.team2.movieproject.exception.MovieNotFoundException;
import com.cursor.team2.movieproject.exception.ReviewAlreadyExistsException;
import com.cursor.team2.movieproject.exception.ReviewNotFoundException;
import com.cursor.team2.movieproject.repository.MovieRepository;
import com.cursor.team2.movieproject.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    public Review addReview(Review review) {
        if(!movieRepository.existsById(review.getMovie().getId())){
            throw new MovieNotFoundException(review.getMovie().getId());
        }
        if (reviewRepository.existsById(review.getId())) {
            throw new ReviewAlreadyExistsException(review.getId());
        } else {
            return reviewRepository.save(review);
        }
    }

    public Review getById(long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    public void deleteReview(long id) {
        reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        reviewRepository.deleteById(id);
    }

    public Review updateReview(Review review) {
        if (reviewRepository.existsById(review.getId())) {
            return reviewRepository.save(review);
        } else {
            throw new ReviewNotFoundException(review.getId());
        }
    }


}
