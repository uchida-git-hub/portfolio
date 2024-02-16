package com.example.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeleteForm {

	@NotNull(message = "IDを入力してください")
	@Size(min = 5,max = 20 ,message = "IDは5文字以上、20文字以下で入力してください")
	private String userId;
	
	@NotNull
	private boolean flg;
}
