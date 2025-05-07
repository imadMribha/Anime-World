package com.animeworld.animeapp.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.animeworld.animeapp.service.JwtService;
import com.animeworld.animeapp.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	
	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService;
	
	public SecurityConfig(JwtService jwtService, UserDetailsServiceImpl iserDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = iserDetailsService;
	}
	
	//Main Security filter chain
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/**").permitAll() // Allow login/register
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	//Auth manager bean (used in login service)
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	//Jwt filter to run on every request
	public OncePerRequestFilter jwtAuthFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					                        HttpServletResponse response,
					                        FilterChain filterChain) throws ServletException, IOException {
				final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
				
				if (authHeader == null || !authHeader.startsWith("Bearer ")) {
					filterChain.doFilter(request, response);
					return;
				}
				
				final String jwt = authHeader.substring(7); // remove the "Bearer " part
				final String username = jwtService.extractUsername(jwt); //  extract the username from the jwt token
				
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username); // load the user details from the database
					
					if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
						
						UsernamePasswordAuthenticationToken authToken =
								new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // create the auth token
						
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // set the details of the auth token
						SecurityContextHolder.getContext().setAuthentication(authToken); // set the auth token in the security context
					}
				}
				// Continue the filter chain
				filterChain.doFilter(request, response); // continue the filter chain
			}
		};
	}
	

}
