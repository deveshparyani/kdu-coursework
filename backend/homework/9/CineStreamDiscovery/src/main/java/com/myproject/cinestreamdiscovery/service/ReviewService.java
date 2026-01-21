package com.myproject.cinestreamdiscovery.service;

import com.myproject.cinestreamdiscovery.entities.Review;
import com.myproject.cinestreamdiscovery.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(int movieId, int rating, String comment) {
        return Review.builder()
                .movieId(movieId)
                .rating(rating)
                .comment(comment)
                .build();
    }

    public double calculateAverageRating(int movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}