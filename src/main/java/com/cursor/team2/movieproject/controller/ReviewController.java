package com.cursor.team2.movieproject.controller;

import com.cursor.team2.movieproject.entity.Review;
import com.cursor.team2.movieproject.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
public class ReviewController {

    public final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable long id) {
        return reviewService.getById(id);
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable long id) {
        reviewService.deleteReview(id);
    }

    @PatchMapping
    public Review updateReview(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }
}
