package com.animeworld.animeapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.animeworld.animeapp.entity.User;
import com.animeworld.animeapp.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		 this.userRepository = userRepository;
	}
	
	@PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
