package com.example.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AccountInfo {
	
	private String userId;
	
	private String password;
	
	//パスワード更新日
	private LocalDate passwordUpdateDate;
	
	//メールアドレス
	private String mailAddress;
	
	//アカウントのロック状態
	private boolean unlocked;
	
	//有効期限
	private LocalDate dateOfExpiry;
	
	//ログイン失敗回数
	private int loginFailed;
	
	//有効無効フラグ
	private boolean enabled;
	
	//独自のフィールド
	
	//プロフィール
	public String profile;
	
	//ユーザー名
	public String appUserName;
	
	
}
