package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.Userdata;

@Mapper
public interface FollowMapper {
	
	//一人をフォローする
	public int followUserOne(String userId , String follower);
	
	//フォローの解除
	public int deleteFollowOne(String userId , String follower);
	
	//フォローしているユーザーの一覧取得
	public List<Userdata>selectFollowUserId(String userId);
	
	//フォローされているユーザーの一覧取得
	public List<Userdata>selectFollowerByUserId(String userId);
	
	//フォローの削除
	public void deleteFollowByFollwer(String follower);
}
