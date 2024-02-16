package com.example.model;

import lombok.Data;

@Data
public class Follow {
	
	//フォローされているユーザーの情報
	private String follower;
	
	//フォローしているユーザーの情報
	private String usreId;
}
