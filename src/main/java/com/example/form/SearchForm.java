package com.example.form;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SearchForm {
	
	@Size(max = 30)
	private String keyword;
	
	private int postCategoryId;
	
	private int techCategoryId;


}
