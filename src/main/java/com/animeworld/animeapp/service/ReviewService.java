package com.animeworld.animeapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.animeworld.animeapp.dto.ReviewDto;
import com.animeworld.animeapp.entity.*;
import com.animeworld.animeapp.repository.*;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final AnimeRepository animeRepository;
	private final UserRepository userRepository;
	
	public ReviewService(ReviewRepository reviewRepository, AnimeRepository animeRepository, UserRepository userRepository) {
		this.reviewRepository = reviewRepository;
		this.animeRepository = animeRepository;
		this.userRepository = userRepository;
	}
	
	public ReviewDto addReview(ReviewDto reviewDto) {
		User user = getCurrentUser();
		Anime anime = animeRepository.findById(reviewDto.getAnimeId())
				.orElseThrow(() -> new RuntimeException("Anime not found"));
		
		Review review = Review.builder()
				.anime(anime)
				.user(user)
				.content(reviewDto.getContent())
				.rating(reviewDto.getRating())
				.createdAt(LocalDateTime.now())
				.build();
		
		reviewRepository.save(review);
		return mapToDto(review);
	}
	
	public List<ReviewDto> getReviewsByAnime(Long animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime not found"));

        return reviewRepository.findByAnime(anime).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
	
	 private ReviewDto mapToDto(Review review) {
		 ReviewDto dto = new ReviewDto();
	     dto.setAnimeId(review.getAnime().getId());
	     dto.setUsername(review.getUser().getUsername());
	     dto.setContent(review.getContent());
	     dto.setRating(review.getRating());
	     dto.setCreatedAt(review.getCreatedAt());
	     
	     return dto;    
	 }
	 
	 private User getCurrentUser() {
		 String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		 return userRepository.findByUsername(username).orElseThrow();
	 }
}
