package com.myproject.cinestreamdiscovery.repositories;

import com.myproject.cinestreamdiscovery.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
}