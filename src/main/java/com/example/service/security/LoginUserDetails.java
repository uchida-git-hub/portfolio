package com.example.service.security;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDetails implements UserDetails{
	
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
	
	//ユーザー名
	public String appUserName;
	
	private Collection<? extends GrantedAuthority> authority;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
//		return roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
	}
	

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}
	
	/**
	 * アカウント有効期限のチェック
	 * true:有効　, false:無効
	 */
	@Override
	public boolean isAccountNonExpired() {
		
		if(this.dateOfExpiry.isAfter(LocalDate.now())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * アカウントのアンロックの情報
	 * true : ロックされていない　,false : ロックされている状態
	 */
	@Override
	public boolean isAccountNonLocked() {
		return this.unlocked;
	}
	
	/**
	 * パスワードの有効期限のチェック
	 * true : 有効期限内
	 * false : 有効期限切れ
	 */
	@Override
	public boolean isCredentialsNonExpired() {
//		if(this.passwordUpdateDate.isAfter(LocalDate.now())) {
//			//有効期限が現在時刻より後なら有効
//			return true;
//		}else {
//			//有効期限が現在時刻より前なら無効
//			return false;
//		}
		return true;
	}
	
	/**
	 * アカウントの有効,無効の確認
	 * true : 有効 , false : 無効
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
