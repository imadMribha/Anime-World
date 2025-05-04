package com.animeworld.animeapp.repository;

import com.animeworld.animeapp.entity.Review;
import com.animeworld.animeapp.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAnime(Anime anime);
}

