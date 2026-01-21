package com.myproject.cinestreamdiscovery.controller;


import com.myproject.cinestreamdiscovery.entities.Movie;
import com.myproject.cinestreamdiscovery.service.MovieService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @QueryMapping("getMovieById")
    public Movie getMovieById(@Argument int movieId){
        return movieService.getMovieById(movieId);
    }

    @QueryMapping("getAllMovies")
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

}
