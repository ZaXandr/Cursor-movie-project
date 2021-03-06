package com.cursor.team2.movieproject.dto;


import com.cursor.team2.movieproject.entity.Rate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MoviePageDto {

    public long id;
    public String name;
    public String category;
    public String director;
    public String description;
    public Rate rate;
    public List<ReviewDto> reviews;
}
