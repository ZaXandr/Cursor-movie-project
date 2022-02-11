package com.cursor.team2.movieproject.repository;

import com.cursor.team2.movieproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r WHERE r.movie.id = :id")
    public List<Review> getByMovieId(long id);
}
