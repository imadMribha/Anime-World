package com.animeworld.animeapp.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.animeworld.animeapp.dto.*;
import com.animeworld.animeapp.entity.*;
import com.animeworld.animeapp.repository.*;

@Service
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final UserRepository userRepository;
	private final DiscussionRepository discussionRepository;
	
	public ReplyService(ReplyRepository replyRepository, UserRepository userRepository, DiscussionRepository discussionRepository) {
		this.replyRepository = replyRepository;
		this.userRepository = userRepository;
		this.discussionRepository = discussionRepository;
	}
	
	public ReplyDto addReply(Long discussionId, ReplyDto replyDto) {
		User user = getCurrentUser();
		
		Discussion discussion = discussionRepository.findById(discussionId)
				.orElseThrow(() -> new RuntimeException("Discussion not found"));
		
		Reply reply = Reply.builder()
				.content(replyDto.getContent())
				.createdAt(LocalDateTime.now())
				.user(user)
				.discussion(discussion)
				.build();
		
		replyRepository.save(reply);
		
		replyDto.setUsername(user.getUsername());
		replyDto.setCreatedAt(reply.getCreatedAt());
		
		return replyDto;
	}
	
	private User getCurrentUser() {
		String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        return userRepository.findByUsername(username).orElseThrow();
	}
}
