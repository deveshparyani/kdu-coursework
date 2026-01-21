package com.myproject.cinestreamdiscovery.repositories;

import com.myproject.cinestreamdiscovery.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}