package com.animeworld.animeapp.dto;

import lombok.Data;

@Data
public class AnimeListItemDto {

	private Long animeId;
	private String title;
	private String imageUrl;
	private String status;
	private Integer progress;
}
