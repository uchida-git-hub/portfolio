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
public class CommentForm implements Serializable{
	
	@NotEmpty
	@PostVaild
	@Size(min=1, max = 100 , message = "100文字いないで入力してください")
	private String commentText;
	
	private int postId;

}
