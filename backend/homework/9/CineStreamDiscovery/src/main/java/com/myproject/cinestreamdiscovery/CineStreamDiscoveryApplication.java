package com.myproject.cinestreamdiscovery;

import com.myproject.cinestreamdiscovery.entities.Director;
import com.myproject.cinestreamdiscovery.entities.Movie;
import com.myproject.cinestreamdiscovery.repositories.DirectorRepository;
import com.myproject.cinestreamdiscovery.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CineStreamDiscoveryApplication implements CommandLineRunner {

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorRepository directorRepository;

    public static void main(String[] args) {
        SpringApplication.run(CineStreamDiscoveryApplication.class, args);
    }

    @Override
    public void run(String[] args) {

        Director d1 = new Director();
        d1.setName("Christopher Nolan");
        d1.setTotalAwards(34);

        Director d2 = new Director();
        d2.setName("Denis Villeneuve");
        d2.setTotalAwards(18);

        Director d3 = new Director();
        d3.setName("Peter Jackson");
        d3.setTotalAwards(56);

        directorRepository.save(d1);
        directorRepository.save(d2);
        directorRepository.save(d3);

        Movie m1 = new Movie();
        m1.setTitle("Inception");
        m1.setGenre("Sci-fi");
        m1.setDirectorId(d1.getId());

        Movie m2 = new Movie();
        m2.setTitle("Dune");
        m2.setGenre("Sci-fi");
        m2.setDirectorId(d2.getId());

        Movie m3 = new Movie();
        m3.setTitle("Lord of the Rings");
        m3.setGenre("Fantasy");
        m3.setDirectorId(d3.getId());

        movieService.addMovie(m1);
        movieService.addMovie(m2);
        movieService.addMovie(m3);
    }

}
