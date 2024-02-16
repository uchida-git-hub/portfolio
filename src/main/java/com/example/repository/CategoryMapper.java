package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.PostCategory;
import com.example.model.TechCategory;

@Mapper
public interface CategoryMapper {
	
	//技術カテゴリー一覧を検索
	public List<TechCategory> selectAllTechCategory();
	
	//ポストカテゴリ一覧を検索
	public List<PostCategory> selectAllPostCategory();
	
	//ポストの投稿
	public void uploadTechCategory(int techCategoryId , int postId);
	
	public void uploadPostCategory(int postCategoryId , int postId);
	
	//ポストの削除に使用
	public void deleteTechCategoryByPostId(int postId);
	
	public void deletePostCategoryByPostId(int postId);
	
	//ポストの更新
	public void updateTechCategory(int postId , int techCategoryId);
	
	public void updatePostCategory(int postId , int postCategoryId);
}
