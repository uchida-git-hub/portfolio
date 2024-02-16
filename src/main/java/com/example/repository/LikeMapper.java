package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.PostInfo;

@Mapper
public interface LikeMapper {
	
	
	//いいねする
	public void insertLike(int postId , String userId);
	
	//いいねしたユーザー一覧を返す
	public List<String> selectUseridByPostId(int postId);
	
	//いいねした投稿のpost一覧を取得
	public List<PostInfo> selectPostInfoByUserId(String userId);
	
	//いいねの取り消し
	public void deleteLike(int postId , String userId);
	
	//いいねの削除(退会時に使用)
	public void deleteLikeByUserId(String userId);
	
	//いいねの削除(postの削除の際にいいねも削除
	public void deleteLikeByPostId(int postId);
	
}
