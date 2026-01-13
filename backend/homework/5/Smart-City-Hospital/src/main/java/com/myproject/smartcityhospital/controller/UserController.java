package com.myproject.smartcityhospital.controller;


import com.myproject.smartcityhospital.model.User;
import com.myproject.smartcityhospital.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userRepo.save(user);
    }

    @GetMapping
    public List<User> getUsers(
            @RequestParam UUID tenantId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return userRepo.findByTenantPaginated(
                tenantId, page, size, sort
        );
    }

    @PutMapping("/{id}")
    public void updateUsername(
            @PathVariable UUID id,
            @RequestParam String username
    ) {
        userRepo.updateUsername(id, username);
    }
}
