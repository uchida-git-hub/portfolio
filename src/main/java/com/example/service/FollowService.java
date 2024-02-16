package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Userdata;
import com.example.repository.FollowMapper;

@Service
public class FollowService {
	
	private final FollowMapper followMapper;
	
	public FollowService(FollowMapper followMapper) {
		this.followMapper = followMapper;
	}
	
	//フォローする
	@Transactional
	public void followUser(String userId , String follower) {
		followMapper.followUserOne(userId, follower);
		
	}
	
	//フォローを解除する
	@Transactional
	public void deleteFollow(String userId , String follower) {
		followMapper.deleteFollowOne(userId, follower);
	}
	
	//フォローしているユーザーを検索
	@Transactional
	public List<Userdata> searchByUserId(String userId){
		List<Userdata>follows = followMapper.selectFollowUserId(userId);
		return follows;
	}
	
	//フォローされているユーザーを検索
	@Transactional
	public List<Userdata> seachfollower(String userId){
		List<Userdata>followers = followMapper.selectFollowerByUserId(userId);
		return followers;
	}
	
	@Transactional
	public void deleteFollowByFollower(String Follower) {
		followMapper.deleteFollowByFollwer(Follower);
	}

}