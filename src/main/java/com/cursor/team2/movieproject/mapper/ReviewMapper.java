package com.cursor.team2.movieproject.mapper;

import com.cursor.team2.movieproject.dto.ReviewDto;
import com.cursor.team2.movieproject.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDto toDto(Review review){
        ReviewDto reviewDto = new ReviewDto()
                .setReview(review.getReview())
                .setLiked(review.isLiked());
        return reviewDto;
    }
}
