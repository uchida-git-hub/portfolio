package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.repository.RoleMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleMapper roleMapper;
	
	//ユーザーの権限を登録する
	@Transactional
	public void signupRoleUser(int roleId , String userId) {
		roleMapper.addRoleUser(userId, roleId);
	}
	
	//ユーザーの削除
	@Transactional
	public void deleteRoleUser(String userId) {
		roleMapper.deleteRoleUser(userId);
	}
}
