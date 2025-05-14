package com.animeworld.animeapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animeworld.animeapp.dto.AnimeDto;
import com.animeworld.animeapp.service.AnimeService;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {

	private final AnimeService animeService;
	
	public AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}
	
	@GetMapping
	public ResponseEntity<List<AnimeDto>> getAllAnime() {
		 return ResponseEntity.ok(animeService.getAllAnime());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AnimeDto> getAnime(@PathVariable Long id) {
		return ResponseEntity.ok(animeService.getAnimeById(id));
	}
	
	@PostMapping
	public ResponseEntity<AnimeDto> createAnime(@RequestBody AnimeDto dto) {
		return ResponseEntity.ok(animeService.createAnime(dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
		animeService.deleteAnime(id);
		return ResponseEntity.noContent().build();
	}
}
