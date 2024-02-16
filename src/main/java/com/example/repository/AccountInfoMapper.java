package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.AccountInfo;
import com.example.model.Userdata;

@Mapper
public interface AccountInfoMapper {
	
	//ユーザー登録1件
	public int insertUserOne(AccountInfo acountInfo);
	
	//ユーザー削除1件
	public int deleteUser(String userId);
	
	//ユーザー一件検索
	public AccountInfo findByUserId(String userId);
	
	//パスワード更新
	public int updatePassword(String userId , String password , LocalDate passwordUpdateDate);
	
	//ログイン失敗回数とロック
	public int updateUnlock(String userId , int loginFailed , boolean unlock);
	
	//プロフィール画面の表示に使う
	public Userdata selectUserOne(String userId);
	
	//プロフィールの更新
	public void updateProfile(String userId , String appUserName , String profile);
	
	//アドミン専用
	//ロックされているユーザーの検索
	public List<Userdata> selectLockList();
	
	//凍結しているアカウントの検索
	public List<Userdata> selectEnableList();
	
	//ロックの解除
	public void unlock(String userId);
	
	//凍結の解除
	public void unenable(String userId);
	
	//ログイン失敗回数のリセット
	public void resetLoginFail(String userId);
	
	//アカウントの検索
	public List<Userdata> searchUser(List<String> keywords);
	
	//アカウントの停止
	public void enable(String userId);
	
	//管理者としてプロフィール画面を取得する
	public Userdata	selectUserOneByAdomin(String userId);
	
}
