package com.example.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostUpdateForm {
	 
	private String userId;
	
	private int postId;
	
	@NotNull
	@Size(min=1, max = 500 , message = "1文字以上500文字以内で入力してください")
	private String message;
	
	private int postCategoryId;
	
	private int techCategoryId;
}
