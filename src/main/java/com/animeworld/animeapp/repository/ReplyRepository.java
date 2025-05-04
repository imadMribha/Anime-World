package com.animeworld.animeapp.repository;

import com.animeworld.animeapp.entity.Reply;
import com.animeworld.animeapp.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByDiscussion(Discussion discussion);
}