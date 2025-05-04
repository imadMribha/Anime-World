package com.animeworld.animeapp.repository;

import com.animeworld.animeapp.entity.Discussion;
import com.animeworld.animeapp.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    List<Discussion> findByAnime(Anime anime);
}