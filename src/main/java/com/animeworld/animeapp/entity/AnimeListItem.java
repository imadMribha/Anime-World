package com.animeworld.animeapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "anime_list_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimeListItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Relation to User
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	//Relation to Anime
	@ManyToOne
	@JoinColumn(name = "anime_id", nullable = false)
	private Anime anime;

	@Column(nullable = false)
	private String status; // e.g., "Watching", "Completed", "On Hold", "Dropped", "Plan to Watch"
	
	private int progress; // e.g., number of episodes watched
}
