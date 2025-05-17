package com.animeworld.animeapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long animeId;
    private String username; // Reviewer name
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
}
// The ReviewDto class represents a data transfer object for reviews.