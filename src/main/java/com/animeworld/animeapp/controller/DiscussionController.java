package com.animeworld.animeapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animeworld.animeapp.dto.DiscussionDto;
import com.animeworld.animeapp.service.DiscussionService;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

	private final DiscussionService discussionService;
	
	public DiscussionController(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}
	
	@PostMapping
	public ResponseEntity<DiscussionDto> create(@RequestBody DiscussionDto discussionDto) {
		return ResponseEntity.ok(discussionService.createDiscussion(discussionDto));
	}
	
	@GetMapping
	public ResponseEntity<List<DiscussionDto>> getAll() {
		return ResponseEntity.ok(discussionService.getAllDiscussions());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DiscussionDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(discussionService.getDiscussionById(id));
	}
}
