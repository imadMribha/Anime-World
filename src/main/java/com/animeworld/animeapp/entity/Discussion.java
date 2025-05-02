package com.animeworld.animeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discussions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createdAt;

    // Who posted it
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
 // Optional relation to a specific anime
    @ManyToOne
    @JoinColumn(name = "anime_id")
    private Anime anime;

    // Replies under this discussion
    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL)
    private List<Reply> replies;
}
