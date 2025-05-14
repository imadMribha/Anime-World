package com.animeworld.animeapp.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AnimeDto {

	private Long id;
	private String title;
	private String description;
	private String genre;
	private LocalDate releaseDate;
	private String status;
	private String imageUrl;
}
