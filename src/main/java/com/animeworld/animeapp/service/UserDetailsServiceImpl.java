package com.animeworld.animeapp.service;

import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;

import com.animeworld.animeapp.entity.User;
import com.animeworld.animeapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Implement the loadUserByUsername method to load user details from the database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userRepository.findByUsername(username)
    			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    	
    	return new org.springframework.security.core.userdetails.User(
    			user.getUsername(),
    			user.getPassword(),
    			Collections.singletonList(() -> "ROLE_" + user.getRole())
    	);
    }
    

}