package com.example.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class CommentInfo {
	
	private int commentId;
	
	private int postId;
	
	private String userId;
	
	private String appUserName;
	
	private String message;
	
	private LocalDateTime created;
	
	public List<String> splitComment(){
		List<String> spComment = Arrays.asList(this.message.split("\r\n|\r|\n" , -1));
		return spComment;
 	}
	
}