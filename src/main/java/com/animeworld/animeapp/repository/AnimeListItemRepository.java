package com.animeworld.animeapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animeworld.animeapp.entity.Anime;
import com.animeworld.animeapp.entity.AnimeListItem;
import com.animeworld.animeapp.entity.User;

public interface AnimeListItemRepository extends JpaRepository<AnimeListItem, Long> {
	
	List<AnimeListItem> findByUser(User user);
	Optional<AnimeListItem> findByUserAndAnime(User user, Anime anime);
}
