package com.example.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostInfo {
	
	private int postId;
	
	private String userId;
	
	private String appUserName;
	
	private String message;
	
	private String techCategory;
	
	private String postCategory;
	
	private LocalDateTime created;
	
	private List<String> userList;
	
	//messageを改行で分割してListとして返す
	public List<String> splitMessage(){
		List<String> spMessage=Arrays.asList(this.message.split("\r\n|\r|\n" , -1));
		return spMessage;	
	}
	
}
