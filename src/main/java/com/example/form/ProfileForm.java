package com.example.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileForm {
	
	@NotNull
	@Size(min = 1 , max = 20 , message = "ユーザー名は1文字以上20文字以内で入力してください")
	private String appUserName;
	
	@NotNull
	@Size(min=1, max = 200 , message = "プロフィールは1文字以上200文字以内で入力してください")
	private String profile;

}
