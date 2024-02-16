package com.example.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.AccountInfo;
import com.example.repository.AccountInfoMapper;
import com.example.repository.RoleMapper;
import com.example.service.security.LoginUserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class LoginUserService {
	
	private final RoleMapper roleMapper;
	
	private final AccountInfoMapper accountInfoMapper;
	
	/**
	 * userInfoとAuthorityを取得してUserDetailsを生成する
	 * @param userId
	 * @return UserDetails
	 */
	public UserDetails selectUserOne(String userId) {

		
		AccountInfo accountInfo =  accountInfoMapper.findByUserId(userId);
		if(accountInfo == null) {
			throw new UsernameNotFoundException(userId +"is not found.");
		}
		
		String role = roleMapper.getRole(userId);
		if(role == null) {	
			throw new UsernameNotFoundException(role + "is not found.");
		}
		
		Collection<? extends GrantedAuthority> authorityList = 
				AuthorityUtils.createAuthorityList("ROLE_" + role);
		
		LoginUserDetails user = new LoginUserDetails();
		user.setAppUserName(accountInfo.getAppUserName());
		user.setPassword(accountInfo.getPassword());
		user.setMailAddress(accountInfo.getMailAddress());
		user.setUnlocked(accountInfo.isUnlocked());
		user.setDateOfExpiry(accountInfo.getDateOfExpiry());
		user.setLoginFailed(accountInfo.getLoginFailed());
		user.setEnabled(accountInfo.isEnabled());
		user.setUserId(accountInfo.getUserId());
		user.setAuthority(authorityList);
		user.setPasswordUpdateDate(accountInfo.getPasswordUpdateDate());
		
		return user;
	}
	
	/**
	 * パスワードの更新
	 */
	public int updatePassword(String userId , 
								String password ,
								LocalDate passwordupdateDate) {
		
		int result = accountInfoMapper.updatePassword(userId, password , passwordupdateDate);
		return result;
	}
	
	/**
	 * ログイン失敗回数を更新する
	 * @param userId
	 * @param loginMissTime
	 * @param unlock
	 * @return
	 */
	public int updateUnlock(String userId , int loginMissTime ,boolean unlock) {
		//失敗回数の更新
		int result = accountInfoMapper.updateUnlock(userId, loginMissTime, unlock);
		return result;
	}

}
