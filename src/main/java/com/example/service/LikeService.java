package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.PostInfo;
import com.example.repository.LikeMapper;

@Service
public class LikeService {
	
	private final LikeMapper likeMapper;
	
	public LikeService (LikeMapper likeMapper) {
		this.likeMapper=likeMapper;
	}
	
	
	//いいねを追加
	@Transactional
	public void insertLike(int postId , String userId) {
		likeMapper.insertLike(postId, userId);
	}
	
	//いいねの取り消し
	@Transactional
	public void deleteLike(int postId , String userId) {
		likeMapper.deleteLike(postId, userId);
	}
	
	//いいねしたpostの一覧を取得
	@Transactional
	public List<PostInfo> selectPostInfoByUserId(String userId){
		List<PostInfo>postIds = likeMapper.selectPostInfoByUserId(userId);
		return postIds;
	}
	
	//いいねしたユーザーのID一覧を取得
	@Transactional
	public List<String> selectUserIdBypostId(int postId) {
		List<String>likedusers = likeMapper.selectUseridByPostId(postId);
		return likedusers;
	}
	
	//いいねの削除
	@Transactional
	public void deleteLikeByUserId(String userId) {
		likeMapper.deleteLikeByUserId(userId);
	}
	
	//いいねの削除(postの削除の際に使用
	@Transactional
	public void deleteLikeByPostId(int postId) {
		likeMapper.deleteLikeByPostId(postId);
	}
	
	
}
