package com.example.form;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSearchForm {
	
	@Size(min = 1 , max = 30 ,message ="キーワードは1文字以上30文字以下で入力してください" )
	@NotNull
	private String keyword;
	
	public List<String> splitKeyword(){
		List<String> keywords = Arrays.asList(this.keyword.replaceAll("　", " ")
													.replaceAll("\\s{2,}", " ")
													.split(" "));
		
		return keywords;
	}
	
}
