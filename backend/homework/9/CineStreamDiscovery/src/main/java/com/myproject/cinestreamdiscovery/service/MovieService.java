package com.myproject.cinestreamdiscovery.service;

import com.myproject.cinestreamdiscovery.entities.Movie;
import com.myproject.cinestreamdiscovery.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie getMovieById(int movieId){
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException(""));
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public void addMovie(Movie movie){
        movieRepository.save(movie);
    }

}
