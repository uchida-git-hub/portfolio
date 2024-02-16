package com.example.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginForm {
	
	@NotEmpty(message = "IDを入力してください")
	@Size(min = 5,max = 20 ,message = "IDは5文字以上、20文字以下で入力してください")
	private String userId;
	
	@NotEmpty(message = "パスワードを入力してください" )
	@Size(min = 10 ,max = 20 ,message = "IDは5文字以上、20文字以下で入力してください")
	private String password;

}
