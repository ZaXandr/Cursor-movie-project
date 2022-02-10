package com.cursor.team2.movieproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MovieDto {

    public long id;
    public String name;
    public String category;
    public String director;
    public String description;


}