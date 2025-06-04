package com.animeworld.animeapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.animeworld.animeapp.entity.*;
import com.animeworld.animeapp.dto.*;
import com.animeworld.animeapp.repository.*;


@Service
public class DiscussionService {
	private final DiscussionRepository discussionRepository;
    private final ReplyRepository replyRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    public DiscussionService(DiscussionRepository discussionRepository,
                             ReplyRepository replyRepository,
                             AnimeRepository animeRepository,
                             UserRepository userRepository) {
        this.discussionRepository = discussionRepository;
        this.replyRepository = replyRepository;
        this.animeRepository = animeRepository;
        this.userRepository = userRepository;
    }
    
    // Method to create a new discussion
    public DiscussionDto createDiscussion(DiscussionDto discussionDto) {
    	User user = getCurrentUser();
    	Anime anime = null;
    	if (discussionDto.getAnimeId() != null) {
    		anime = animeRepository.findById(discussionDto.getAnimeId())
    				.orElseThrow(() -> new RuntimeException("Anime not found"));
    	}
    	
    	Discussion discussion = Discussion.builder()
				.title(discussionDto.getTitle())
				.content(discussionDto.getContent())
				.user(user)
				.anime(anime)
				.createdAt(LocalDateTime.now())
				.build();
		
		discussionRepository.save(discussion);
		
		return mapToDto(discussion);
    }
    
    public DiscussionDto getDiscussionById(Long id) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));
        return mapToDto(discussion);
    }
    
    public List<DiscussionDto> getAllDiscussions() {
        return discussionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    private DiscussionDto mapToDto(Discussion discussion) {
    	DiscussionDto discussionDto = new DiscussionDto();
    	discussionDto.setId(discussion.getId());
    	discussionDto.setTitle(discussion.getTitle());
    	discussionDto.setContent(discussion.getContent());
    	discussionDto.setCreatedAt(discussion.getCreatedAt());
    	discussionDto.setUsername(getCurrentUser().getUsername());
    	discussionDto.setAnimeId(discussion.getAnime() != null ? discussion.getAnime().getId() : null);
    	
    	// Map replies to DTO
    	List<ReplyDto> replies = discussion.getReplies().stream()
    			.map(reply -> {
    				ReplyDto replyDto = new ReplyDto();
    				replyDto.setUsername(reply.getUser().getUsername());
    				replyDto.setContent(reply.getContent());
    				replyDto.setCreatedAt(reply.getCreatedAt());
    				return replyDto;
    			})
    			.collect(Collectors.toList());
    	
    	// Set replies in discussion DTO
    	discussionDto.setReplies(replies);
    	return discussionDto;
    }
    
    private User getCurrentUser() {
    	String username = ((UserDetails) SecurityContextHolder.getContext()
    			.getAuthentication().getPrincipal()).getUsername();
    	
    	return userRepository.findByUsername(username).orElseThrow();
    }
}
