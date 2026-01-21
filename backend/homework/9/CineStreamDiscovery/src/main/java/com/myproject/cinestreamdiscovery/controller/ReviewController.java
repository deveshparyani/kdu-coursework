package com.myproject.cinestreamdiscovery.controller;

import com.myproject.cinestreamdiscovery.entities.Review;
import com.myproject.cinestreamdiscovery.service.MovieService;
import com.myproject.cinestreamdiscovery.service.ReviewService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @MutationMapping
    public Review addReview(@Argument int movieId,
                            @Argument int rating,
                            @Argument String comment) {
        return reviewService.addReview(movieId, rating, comment);
    }
}