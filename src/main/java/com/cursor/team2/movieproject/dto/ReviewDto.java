package com.cursor.team2.movieproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ReviewDto {

    private String review;
    private boolean liked;
}
