package com.example.component;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.service.security.LoginUserDetails;
import com.example.service.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;


/**
 *ログイン失敗時の処理
 *ログインに用いようとしたユーザーIDを取得しDBを検索する
 *存在しないユーザーの場合は例外をスローする
 *ユーザーIDが存在する場合は該当するアカウントのログイン失敗回数を増やす処理を行う
 */
@Component
@RequiredArgsConstructor
public class BadCredentialsEventListener{

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	@EventListener
	public void onBadCredenitalsEvent(AuthenticationFailureBadCredentialsEvent event) {
		
		//ユーザーが存在するかどうかの判定
		if(event.getException().getClass()
				.equals(UsernameNotFoundException.class)) {
			return;
		}
		
		//ユーザーIDの取得
		String userId = event.getAuthentication().getName();
		
		//ユーザー情報の取得
		LoginUserDetails user = (LoginUserDetails)userDetailsServiceImpl.loadUserByUsername(userId);
		
		//ログイン失敗回数を増やす
		int loginMissTime = user.getLoginFailed() + 1;
		userDetailsServiceImpl.updateUnlock(userId, loginMissTime);
		
	}
	
}
