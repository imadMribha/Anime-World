package com.animeworld.animeapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Relation to User
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// Relation to Anime
	@ManyToOne
	@JoinColumn(name = "anime_id", nullable = false)
	private Anime anime;

	@Column(columnDefinition = "TEXT" ,nullable = false)
	private String content;

	private int rating; // e.g., 0 to 10

	private LocalDateTime createdAt;
}
