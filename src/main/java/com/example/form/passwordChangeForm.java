package com.example.form;

import com.example.validator.IsAlphabet;
import com.example.validator.PasswordValid;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class passwordChangeForm {
	
	@NotNull(message = "IDを入力してください")
	@IsAlphabet
	@Size(min = 5,max = 20 ,message = "IDは5文字以上、20文字以下で入力してください")
	private String userId;
	
	@NotNull(message = "パスワードを入力してください" )
	@PasswordValid
	private String password;
}
