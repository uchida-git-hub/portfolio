package com.example.model;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Userdata {
	
	private String userId;
	private String appUserName;
	private String profile;
	
	//profileを改行で切り分けてListとして返す
	public List<String> splitProfile(){
		
		String profile= this.profile;
		List<String> spProfile =Arrays.asList(profile.split("\r\n|\r|\n" , -1));
		return spProfile;
	}

}
