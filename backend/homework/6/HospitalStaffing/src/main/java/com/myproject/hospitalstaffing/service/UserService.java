package com.myproject.hospitalstaffing.service;

import com.myproject.hospitalstaffing.entities.User;
import com.myproject.hospitalstaffing.repositories.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User save(User user){
        return userRepo.save(user);
    }
}
