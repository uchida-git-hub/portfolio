package com.example.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.form.AccountForm;
import com.example.form.DeleteForm;
import com.example.form.passwordChangeForm;
import com.example.model.AccountInfo;
import com.example.service.AccountService;
import com.example.service.CommentService;
import com.example.service.FollowService;
import com.example.service.LikeService;
import com.example.service.PostService;
import com.example.service.security.LoginUserDetails;
import com.example.service.security.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class LoginController {
	
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	private final AccountService accountService;
	
	private final PostService postService;
	
	private final CommentService commentService;
	
	private final LikeService likeService;
	
	private final FollowService followService;
	
	private final PasswordEncoder encoder;
	
	
	//*********************************ログインに関するメソッド***********************************
	
	/*
	 * ログインフォームの取得
	 */
	@GetMapping("/login/login")
	public String loginForm() {
		return "login/login_form";
	}
	
	
	//*********************************アカウントの登録に関するメソッド***************************
	
	
	/*
	 * アカウントの登録に関するメソッド
	 * 登録画面にAccountForm.javaを渡す
	 */
	@GetMapping("/login/signup")
	public String getSignuptForm(Model model ,
								@ModelAttribute AccountForm accountForm) {
		return "login/signup";
	}
	
	
	/*
	 * アカウント登録画面からの入寮内容を元に登録を行う
	 * パスワードの最終更新日時は登録したときにする
	 * ログイン成功後はログイン画面にリダイレクトする
	 */
	@PostMapping("/login/signup")
	public String postSingup(@Validated AccountForm accountForm ,
								BindingResult result , 
							    Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute(accountForm);
			return "login/signup";
		}
		
		
		AccountInfo accountInfo = new AccountInfo();
		
		//パスワードをエンコードする
		String encodePass = encoder.encode(accountForm.getPassword());
		
		//パスワードの有効期限を3カ月後に設定する
		LocalDate localDate = LocalDate.now();
		LocalDate passUpdate = localDate.plusMonths(3);
		
		accountInfo.setAppUserName(accountForm.getAppUserName());
		accountInfo.setPassword(encodePass);
		accountInfo.setProfile(accountForm.getProfile());
		accountInfo.setUserId(accountForm.getUserId());
		accountInfo.setMailAddress(accountForm.getMailAddress());
		accountInfo.setPasswordUpdateDate(passUpdate);
		accountInfo.setUnlocked(true);
		accountInfo.setEnabled(true);
		accountInfo.setLoginFailed(0);
		accountInfo.setDateOfExpiry(LocalDate.of(2099, 1, 1));
		
		accountService.signup(accountInfo);
		
		
		return "redirect:login/login_form";
	}
	
	//****************パスワード変更に関するメソッド**********************************************************
	
	/*
	 * パスワードの変更に関するメソッド
	 * passWordChangeForm.javaを受け取る
	 * エラーがあった場合はエラーページへ
	 * passwordChangeForm.javaのuserIdとログイン中のユーザーのuserIdが一致しない場合エラー
	 * 新しいパスワードと現在のパスワードが一致したらエラー
	 * パスワード変更後はログアウトし、ログイン画面にリダイレクト
	 */
	@Transactional
	@PostMapping("/login/passwordChange")
	public String passwordChange(Model model , 
								 @Validated passwordChangeForm form,
								BindingResult result,
									@AuthenticationPrincipal LoginUserDetails user,
									Authentication authentication,
									HttpServletRequest request,
									HttpServletResponse response) throws ParseException{
		
		if(result.hasErrors()) {
			model.addAttribute("passwordChangeForm", form);
			return "login/password_change";
		}		
		
		//パスワードの暗号化
		String encryptPassword = encoder.encode(form.getPassword());
		
		//パスワードとユーザーの判定
		if(!form.getUserId().equals(user.getUserId()) || form.getPassword().equals(user.getPassword())) {
			model.addAttribute("passwordChangeForm", form);
			return "login/password_change";
		}
		userDetailsServiceImpl.updatePassword(user.getUserId(), encryptPassword);
		return "redirect:/login/login";
		
	}
	
	/*
	 * パスワード変更画面にpasswordChangeFormをセット
	 * パスワード変更画面に遷移
	 */
	@GetMapping("/login/passwordChange")
	public String getpasswordChangeForm(Model model)	{
		passwordChangeForm passwordChangeForm = new passwordChangeForm();
		model.addAttribute(passwordChangeForm);
		return "login/password_change";
	}
	
	
	//***********アカウント削除に関するメソッド********************************************************************************
	
	/*
	 * アカウントの削除に関するメソッド
	 */
	@GetMapping("/login/delete")
	public String getDeleteForm(@ModelAttribute("deleteForm") DeleteForm deleteForm) {
		return "login/delete_form";
	}
	
	/*
	 * アカウント削除のコントローラー
	 * パスワードとIDを引数とする
	 * チェックボックスの結果を確認
	 * ログイン中のユーザーIDとパスワードと一致したらアカウントを削除
	 * アカウント削除成功後はログアウト
	 * ログインフォームにリダイレクト
	 */
	@Transactional
	@PostMapping("/login/delete")
	public String deleteAccount(Model model ,
								@Validated	DeleteForm deleteForm, 
								BindingResult result,	
								@AuthenticationPrincipal LoginUserDetails user ,
								Authentication authentication
								) throws IOException{
		
		if(result.hasErrors()) {
			model.addAttribute(deleteForm);
			return "login/delete_form";
		}
		//チェックボックスの確認
		if(!deleteForm.isFlg()) {
			model.addAttribute(deleteForm);
			return "login/delete_form";
		}
		
		if (deleteForm.getUserId() != user.getUserId()) {
			model.addAttribute(deleteForm);
			return "login/delete_form";
		}
		
		likeService.deleteLikeByUserId(deleteForm.getUserId());
		postService.deletePostByUserId(deleteForm.getUserId());
		commentService.deleteCommentByUserID(deleteForm.getUserId());
		followService.deleteFollowByFollower(deleteForm.getUserId());
		accountService.deleteAccount(deleteForm.getUserId());
		
		return "/logout";
	}
	
}
