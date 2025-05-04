package com.animeworld.animeapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animeworld.animeapp.entity.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

	Optional<Anime> findByTitle(String title);
}
