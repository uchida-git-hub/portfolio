package com.example.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Post {
	
	private int postId;
	
	private String message;
	
	private String userId;
	
	private LocalDateTime created;
	
}
