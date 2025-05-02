package com.animeworld.animeapp.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String genre;

	private String status; // e.g., "Ongoing", "Completed"

	private LocalDate releaseDate;

	private String imageUrl;

	// Relations
	@OneToMany(mappedBy = "anime", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "anime", cascade = CascadeType.ALL)
	private List<Discussion> discussions;
	
	@OneToMany(mappedBy = "anime", cascade = CascadeType.ALL)
	private List<AnimeListItem> animeListItems;
}
