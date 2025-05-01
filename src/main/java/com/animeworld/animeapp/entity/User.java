package com.animeworld.animeapp.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String role; // e.g., "USER", "ADMIN"
	
	//Relations
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<AnimeListItem> animeList;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Discussion> discussions;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Reply> replies;
}
