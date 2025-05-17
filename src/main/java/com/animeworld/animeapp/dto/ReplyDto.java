package com.animeworld.animeapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDto {
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
// The ReplyDto class represents a data transfer object for replies to reviews.