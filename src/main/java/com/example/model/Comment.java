package com.example.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Comment {
	
	private int commentId;
	
	private String userId;
	
	private String appUserName;

	private int postId;
	
	private String message;
	
	private LocalDateTime created;
	
}
