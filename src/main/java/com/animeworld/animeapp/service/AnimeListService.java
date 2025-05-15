package com.animeworld.animeapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.animeworld.animeapp.dto.AnimeListItemDto;
import com.animeworld.animeapp.entity.Anime;
import com.animeworld.animeapp.entity.AnimeListItem;
import com.animeworld.animeapp.entity.User;
import com.animeworld.animeapp.repository.*;

@Service
public class AnimeListService {

	private final AnimeListItemRepository animeListItemRepository;
	private final UserRepository userRepository;
	private final AnimeRepository animeRepository;
	
	public AnimeListService(AnimeListItemRepository animeListItemRepository,
							UserRepository userRepository,
							AnimeRepository animeRepository) {
		this.animeListItemRepository = animeListItemRepository;
		this.userRepository = userRepository;
		this.animeRepository = animeRepository;
	}
	
	public List<AnimeListItemDto> getUserAnimeList() {
		// this method should return the currently authenticated user's anime list
		User user = getCurrentUser();
		return animeListItemRepository.findByUser(user).stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}
	
	public AnimeListItemDto addOrUpdateAnime(AnimeListItemDto animeListItemDto) {
		// this method should add or update an anime in the user's list
		User user = getCurrentUser();
		Anime anime = animeRepository.findById(animeListItemDto.getAnimeId())
				.orElseThrow(() -> new RuntimeException("Anime not found"));
		
		AnimeListItem animeListItem = animeListItemRepository.findByUserAndAnime(user, anime)
				.orElse(AnimeListItem.builder()
						.user(user)
						.anime(anime)
						.build());
		
		animeListItem.setStatus(animeListItemDto.getStatus());
		animeListItem.setProgress(animeListItemDto.getProgress());
		
		return mapToDto(animeListItemRepository.save(animeListItem));
	}
	
	public void removeAnime(Long animeId) {
		// this method should remove an anime from the user's list
		User user = getCurrentUser();
		Anime anime = animeRepository.findById(animeId)
				.orElseThrow(() -> new RuntimeException("Anime not found"));
		
		// Check if the anime is in the user's list before attempting to delete
		animeListItemRepository.findByUserAndAnime(user, anime)
				.ifPresent(animeListItemRepository::delete);
	}
	
	private User getCurrentUser() {
		// This method should return the currently authenticated user.
		// Load user details
		String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		// Find user by username
		return userRepository.findByUsername(username).orElseThrow();
	}
	
	private AnimeListItemDto mapToDto(AnimeListItem animeListItem) {
		// This method maps an AnimeListItem entity to an AnimeListItemDto
		AnimeListItemDto dto = new AnimeListItemDto();
		dto.setAnimeId(animeListItem.getAnime().getId());
		dto.setTitle(animeListItem.getAnime().getTitle());
		dto.setImageUrl(animeListItem.getAnime().getImageUrl());
		dto.setStatus(animeListItem.getStatus());
		dto.setProgress(animeListItem.getProgress());
		// Add any other fields you want to include in the DTO
		return dto;
	}
}
