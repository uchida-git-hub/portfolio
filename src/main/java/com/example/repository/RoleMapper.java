package com.example.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
	
	public String getRole(String userId);
	
	public void addRoleUser(String userId , int roleId);
	
	public void deleteRoleUser(String userId);

}
