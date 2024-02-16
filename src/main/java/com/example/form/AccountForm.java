package com.example.form;

import java.io.Serializable;

import com.example.validator.IsAlphabet;
import com.example.validator.PasswordValid;
import com.example.validator.UniqueLogin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountForm implements Serializable{
	
	@NotNull(message = "IDを入力してください")
	@UniqueLogin
	@IsAlphabet
	@Size(min = 5,max = 20 ,message = "IDは5文字以上、20文字以下で入力してください")
	private String userId;
	
	@NotNull(message = "パスワードを入力してください" )
	@PasswordValid
	private String password;
	
	@NotNull(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスを入力してください")
	@Size(min = 1)
	private String mailAddress;	
	
	@Size(min=1 ,max = 50 ,message = "プロフィールは50文字までです")
	@NotNull(message = "プロフィールを入力してください")
	private String profile;
	
	@NotNull(message = "ユーザー名を入力してください")
	@Size(min = 1 , max = 20 , message = "ユーザー名は1文字以上20文字以内で入力してください")
	private String appUserName;

}
