package com.animeworld.animeapp.controller;


import com.animeworld.animeapp.dto.ReviewDto;
import com.animeworld.animeapp.service.ReviewService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.addReview(dto));
    }
    
    @GetMapping("/{animeId}")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable Long animeId) {
        return ResponseEntity.ok(reviewService.getReviewsByAnime(animeId));
    }

}
