package com.example.service.security;

import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.service.LoginUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component("UserDetailsServiceImpl")
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final LoginUserService loginUserService;
	
//	private final PasswordEncoder encoder;

	
	//usernameはログイン画面から受け取ったuserId
	@Override
	public UserDetails loadUserByUsername(String username) 
		throws UsernameNotFoundException {
		
		UserDetails user = loginUserService.selectUserOne(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("このユーザーは存在しません");
		}
		
		return user;
		
	}
	
	/**
	 * パスワードのエンコード
	 * パスワードの有効期限を新たに設定
	 * @param userId
	 * @param password
	 */
	public void updatePassword(String userId , String password) throws UsernameNotFoundException{
		
		LocalDate passwordUpdateDate = LocalDate.now().plusMonths(3);
		
		loginUserService.updatePassword(userId, password, passwordUpdateDate);
	}
	
	/**
	 * ログイン失敗回数による判定
	 * ログイン失敗回数は5回まで
	 */
	private static final int loginmissTimeLimit = 5;
	
	@Transactional
	public void updateUnlock(String userId , int loginMissTime) {
		boolean unlock = true;
		
		//ログイン回数の判定
		if(loginMissTime >= loginmissTimeLimit) {
			//フラグの判定
			unlock = false;
		}
		//ログイン失敗回数とアカウントロックの更新
		loginUserService.updateUnlock(userId, loginMissTime , unlock);
		
	}
	
	

}
