package com.animeworld.animeapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.animeworld.animeapp.dto.AnimeDto;
import com.animeworld.animeapp.entity.Anime;
import com.animeworld.animeapp.repository.AnimeRepository;

@Service
public class AnimeService {

	private final AnimeRepository animeRepository;
	
	public AnimeService(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}
	
	public List<AnimeDto> getAllAnime() {
		return animeRepository.findAll().stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}
	
	public AnimeDto getAnimeById(Long id) {
		Anime anime = animeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Anime not found"));
		return mapToDto(anime);
	}
	
	public AnimeDto createAnime(AnimeDto dto) {
		Anime anime = Anime.builder()
				.title(dto.getTitle())
				.description(dto.getDescription())
				.genre(dto.getGenre())
				.releaseDate(dto.getReleaseDate())
				.status(dto.getStatus())
				.imageUrl(dto.getImageUrl())
				.build();
		
		return mapToDto(animeRepository.save(anime));
	}
	
	public void deleteAnime(Long id) {
		animeRepository.deleteById(id);
	}
	
	private AnimeDto mapToDto(Anime anime) {
		AnimeDto dto = new AnimeDto();
		dto.setId(anime.getId());
		dto.setTitle(anime.getTitle());
		dto.setDescription(anime.getDescription());
		dto.setGenre(anime.getGenre());
		dto.setReleaseDate(anime.getReleaseDate());
		dto.setStatus(anime.getStatus());
		dto.setImageUrl(anime.getImageUrl());
		return dto;
	}
}
