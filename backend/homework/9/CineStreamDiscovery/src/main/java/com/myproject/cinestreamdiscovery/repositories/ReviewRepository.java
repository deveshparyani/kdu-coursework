package com.myproject.cinestreamdiscovery.repositories;

import com.myproject.cinestreamdiscovery.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByMovieId(int movieId);
}