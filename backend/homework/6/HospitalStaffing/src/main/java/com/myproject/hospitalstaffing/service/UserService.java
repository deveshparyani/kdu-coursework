package com.myproject.hospitalstaffing.service;

import com.myproject.hospitalstaffing.entities.User;
import com.myproject.hospitalstaffing.repositories.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<User> getUsers(int page, int size){
        if(size < 1 || size > 50){
            throw new IllegalArgumentException(
                    "Size must be between 1 and 50"
            );
        }

        Pageable pageable = PageRequest.of(page, size);

        return userRepo.findAll(pageable);
    }
}
