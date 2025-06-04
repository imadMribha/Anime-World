package com.animeworld.animeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animeworld.animeapp.dto.ReplyDto;
import com.animeworld.animeapp.service.ReplyService;

@RestController
@RequestMapping("/api/discussions/{discussionId}/replies")
public class ReplyController {

	private final ReplyService replyService;
	
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@PostMapping
	public ResponseEntity<ReplyDto> addReply(@PathVariable Long discussionId, @RequestBody ReplyDto replyDto) {
		return ResponseEntity.ok(replyService.addReply(discussionId, replyDto));
	}
}
