package com.animeworld.animeapp.service;

import com.animeworld.animeapp.dto.AuthResponse;
import com.animeworld.animeapp.dto.LoginRequest;
import com.animeworld.animeapp.dto.RegisterRequest;
import com.animeworld.animeapp.entity.User;
import com.animeworld.animeapp.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsServiceImpl userDetailsService;
	
	public AuthService(UserRepository userRepository,
					   JwtService jwtService,
					   AuthenticationManager authenticationManager,
					   PasswordEncoder passwordEncoder,
					   UserDetailsServiceImpl userDetailsService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
	}
	
	public AuthResponse register(RegisterRequest request) {
		User user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role("USER")
				.build();
		
		userRepository.save(user);
		
		String token = jwtService.generateToken(user.getUsername());
		
		return new AuthResponse(token);
	}
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		            request.getUsername(),
		            request.getPassword()
		        )
		    );
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		String token = jwtService.generateToken(userDetails.getUsername());
		
		return new AuthResponse(token);
	}
}
