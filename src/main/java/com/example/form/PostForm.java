package com.example.form;

import java.io.Serializable;

import com.example.validator.PostVaild;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostForm implements Serializable{
	
	private int postCategoryId;
	
	private int techCategoryId;

	@NotEmpty
	@Size(min = 1, max = 499 ,message = "1文字以上500文字以内で入力してください")
	@PostVaild
	private String postText;


}
