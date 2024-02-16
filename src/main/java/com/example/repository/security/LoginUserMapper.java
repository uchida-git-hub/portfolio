package com.example.repository.security;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.AccountInfo;

@Mapper
public interface LoginUserMapper {
	
	public AccountInfo selectUserById(String userId);
	
}
