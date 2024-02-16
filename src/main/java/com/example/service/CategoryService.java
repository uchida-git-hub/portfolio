package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.PostCategory;
import com.example.model.TechCategory;
import com.example.repository.CategoryMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {
	
	private final CategoryMapper categoryMapper;
	
	@Transactional
	public List<TechCategory> selectTechCategory() {
		List<TechCategory> techCategories = categoryMapper.selectAllTechCategory();
		return techCategories;
	}
	
	@Transactional
	public List<PostCategory> selectPostCategories(){
		List<PostCategory>postCategories = categoryMapper.selectAllPostCategory();
		return postCategories;
	}
	
	@Transactional
	public void deleteCategoryByPostId(int postId) {
		categoryMapper.deleteTechCategoryByPostId(postId);
		categoryMapper.deletePostCategoryByPostId(postId);
	}
	
	
	
}
