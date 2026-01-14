package com.myproject.hospitalstaffing.repositories;

import com.myproject.hospitalstaffing.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
}
