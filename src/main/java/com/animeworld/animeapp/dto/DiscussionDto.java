package com.animeworld.animeapp.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DiscussionDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private Long animeId; // optional
    private List<ReplyDto> replies; // 👈 includes all nested replies
}
// The DiscussionDto class represents a data transfer object for discussions.