package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.AccountInfo;
import com.example.model.Userdata;
import com.example.repository.AccountInfoMapper;
import com.example.service.security.LoginUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountService {
	
	private final AccountInfoMapper accountInfoMapper;
	
	private final RoleService roleService;
	
	//アカウント登録
	public void signup(AccountInfo accountInfo) {
		//ロール判定用のID
		int roleId = 0;
		
		boolean result =( accountInfoMapper== null || (accountInfoMapper.findByUserId(accountInfo.getUserId()) == null));
		if(result) {
			accountInfoMapper.insertUserOne(accountInfo);
			
			roleService.signupRoleUser(roleId, accountInfo.getUserId());
			
		}
		
	}
	
	
	/**
	 * ログイン中のアカウントのIDとパスワードが一致すればアカウントを削除
	 * @param userId
	 * @param password
	 * @param user
	 * @throws Exception
	 */
	//アカウント削除
	public void delete(String userId , String password ,@AuthenticationPrincipal LoginUserDetails user)throws Exception {
		if(user.getUserId() == userId && password==user.getPassword()) {
			accountInfoMapper.deleteUser(userId);
		}
	}
	
	/**
	 * パスワード変更の処理
	 * 最終更新日を変更
	 * @param userId
	 * @param password
	 * @param passwordUpdateDate
	 * @throws Exception
	 */
	//パスワード変更
	public void changePassword(String userId , String password ,LocalDate passwordUpdateDate)throws Exception {
		accountInfoMapper.updatePassword(userId, password ,passwordUpdateDate);
	}
	
	/**
	 * ログイン失敗の処理
	 * ログイン失敗回数とロック状態の更新
	 * @param userId
	 * @param loginMisstime
	 * @param unlock
	 * @return
	 */
	//失敗回数の更新
	public int updateUnlock(String userId , int loginMisstime , boolean unlock) {
		int result = accountInfoMapper.updateUnlock(userId, loginMisstime, unlock);
		return result;
	}
	
	/**
	 * プロフィール用のデータの検索
	 * @param userId
	 * @return
	 */
	//プロフィール画面用のデータを取得
	public Userdata selectProfileOne(String userId) {
		Userdata userdata =  accountInfoMapper.selectUserOne(userId);
		return userdata;
	}
	
	//アカウント情報を全て取得
	public AccountInfo findaccountinfoByuserId(String userId) {
		AccountInfo accountInfo = accountInfoMapper.findByUserId(userId);
		return accountInfo;
	}
	
	//ユーザーの削除
	public void deleteAccount(String userId) {
		accountInfoMapper.deleteUser(userId);
	}
	
	//プロフィールの更新
	public void updateProfile(String userId , String appUserName , String profile) {
		accountInfoMapper.updateProfile(userId, appUserName, profile);
		
	}
	
	//ロックされているユーザーの一覧を取得
	public List<Userdata>getLockList(){
		List<Userdata>lockList = accountInfoMapper.selectLockList();
		return lockList;	
	}
	
	//凍結されているユーザーの一覧を取得
	public List<Userdata>getEnableList(){
		List<Userdata> enableList = accountInfoMapper.selectEnableList();
		return enableList;
	}
	
	//ロックの解除
	public void unlock(String userId) {
		accountInfoMapper.unlock(userId);
	}
	
	//凍結の解除
	public void unenable(String userId) {
		accountInfoMapper.unenable(userId);
	}
	
	//アカウントの停止
	public void enable(String userId) {
		accountInfoMapper.enable(userId);
	}
	
	//ログイン失敗回数のリセット
	public void updateLoginFailed(String userId) {
		accountInfoMapper.resetLoginFail(userId);
	}
	
	//ユーザーの検索
	public List<Userdata> searchUser(List<String> keywords){
		List<Userdata>userdatas = accountInfoMapper.searchUser(keywords);
		return userdatas;
	}
	
	//管理者としてユーザーのプロフィールを取得する
	public Userdata selectUserOneByAdomin(String userId) {
		return accountInfoMapper.selectUserOneByAdomin(userId);
	}
	
	

}
