package com.animeworld.animeapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animeworld.animeapp.dto.AnimeListItemDto;
import com.animeworld.animeapp.service.AnimeListService;

@RestController
@RequestMapping("/api/animelist")
public class AnimeListController {

	private final AnimeListService animeListService;
	
	public AnimeListController(AnimeListService animeListService) {
		this.animeListService = animeListService;
	}
	
	@GetMapping
	public ResponseEntity<List<AnimeListItemDto>> getAnimeList() {
        return ResponseEntity.ok(animeListService.getUserAnimeList());
    }
	
	@PostMapping
    public ResponseEntity<AnimeListItemDto> addOrUpdate(@RequestBody AnimeListItemDto dto) {
        return ResponseEntity.ok(animeListService.addOrUpdateAnime(dto));
    }

    @DeleteMapping("/{animeId}")
    public ResponseEntity<Void> removeAnime(@PathVariable Long animeId) {
        animeListService.removeAnime(animeId);
        return ResponseEntity.noContent().build();
    }
}
