package com.cursor.team2.movieproject.repository;

import com.cursor.team2.movieproject.entity.Category;
import com.cursor.team2.movieproject.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("SELECT m FROM Movie m WHERE m.category = :category")
    List<Movie> findByCategory(Category category);

    @Query("SELECT m From Movie m Order By m.rate.average DESC, m.rate.voices DESC")
    List<Movie> getMovieByRatingDesc();

    @Query("SELECT m From Movie m Order By m.rate.average ASC, m.rate.voices ASC")
    List<Movie> getMovieByRatingAsc();

}
