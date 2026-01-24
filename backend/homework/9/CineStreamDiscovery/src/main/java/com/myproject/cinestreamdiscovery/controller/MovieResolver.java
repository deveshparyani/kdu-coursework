package com.myproject.cinestreamdiscovery.controller;

import com.myproject.cinestreamdiscovery.entities.Director;
import com.myproject.cinestreamdiscovery.entities.Movie;
import com.myproject.cinestreamdiscovery.entities.Review;
import com.myproject.cinestreamdiscovery.repositories.DirectorRepository;
import com.myproject.cinestreamdiscovery.service.MovieService;
import com.myproject.cinestreamdiscovery.service.ReviewService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MovieResolver {

    private final DirectorRepository directorRepository;
    private final ReviewService reviewService;
    private final MovieService movieService;

    public MovieResolver(DirectorRepository directorRepository, ReviewService reviewService, MovieService movieService) {
        this.directorRepository = directorRepository;
        this.reviewService = reviewService;
        this.movieService = movieService;
    }

    @SchemaMapping(typeName = "Movie", field = "director")
    public Director getDirector(Movie movie) {
        return directorRepository.findById(movie.getDirectorId())
                .orElse(null);
    }

    @SchemaMapping(typeName = "Movie", field = "averageRating")
    public Double getAverageRating(Movie movie) {
        return reviewService.calculateAverageRating(movie.getId());
    }

    @SchemaMapping(typeName = "Review", field = "movie")
    public Movie getMovie(Review review) {
        return movieService.getMovieById(review.getMovieId());
    }

}