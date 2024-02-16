package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.Post;
import com.example.model.PostInfo;

@Mapper
public interface PostMapper {
	
	//投稿
	public int uploadPost(Post post);
	
	//投稿の編集
	public int updatePost(int postId ,String message);
	
	//ユーザーIDで全件取得
	//投稿の新しい順に取得
	public List<PostInfo> selectAllPostByUserIdOrderByNew (String userId);
	
	//投稿の削除
	public int deletePostByPostId(int postId);
	
	//投稿の削除
	public int deletePostByUserId(String userId);
	
	//投稿の検索
	public List<PostInfo>searchPost(int techCategoryId , int postCategoryId , List<String> keywordsList);
	
	//タイムラインのポストの取得
	public List<PostInfo>selectTimeLine(List<String> follows);
	
	//postIdで検索する
	public PostInfo selectPostInfoByPostId(Integer postId);
		
}
