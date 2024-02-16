package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.form.ProfileForm;
import com.example.form.UserSearchForm;
import com.example.model.PostInfo;
import com.example.model.Userdata;
import com.example.service.AccountService;
import com.example.service.FollowService;
import com.example.service.PostService;
import com.example.service.security.LoginUserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Controller
@Slf4j
public class UserController {
	
	private final AccountService accountService;
	
	private final PostService postService;
	
	private final FollowService followService;

	
	//***************************プロフィールの編集に関するメソッド******************************
	
		/**
		 * プロフィールの編集に関するページ
		 * プロフィール編集用のフォームにprifileForm.javaを渡す
		 * updateuser.htmlに遷移する
		 * @param profileForm
		 * @return
		 */
		@Transactional
		@GetMapping("/user/update")
		public String getUpdateProfile(Model model , Userdata userdata) {
			ProfileForm profileForm = new ProfileForm();
			profileForm.setAppUserName(userdata.getAppUserName());
			profileForm.setProfile(userdata.getProfile());
			model.addAttribute(profileForm);
			return "user/updateuser";
		}
		
		/**
		 * プロフィールの編集
		 * ProfileForm.javaを受け取る
		 * ユーザーIDは認証情報から取得する
		 * エラーがあった場合はupdateuser.htmlを画面を返す
		 * 変更後はmypage.htmlにリダイレクト
		 * @param model
		 * @param result
		 * @param profileForm
		 * @param user
		 * @param redirectAttributes
		 * @return
		 */
		@Transactional
		@PostMapping("/user/update")
		public String postUpdateForm(Model model ,  
									@Validated ProfileForm profileForm , 
									BindingResult result ,
									@AuthenticationPrincipal LoginUserDetails user ,
									RedirectAttributes redirectAttributes) {
			if(result.hasErrors()) {
				model.addAttribute("profileForm" ,profileForm);
				return "user/updateuser";
			}
			//データベースのアカウント情報の更新
			accountService.updateProfile(user.getUserId(), profileForm.getAppUserName(), profileForm.getProfile());
			
			//mypageにリダイレクト
			return "redirect:/user/mypage";
		}
		
		
	//***************************mypageに関するメソッド***************************************
		
		/*
		 * ログイン成功後の遷移ページ
		 * Userdata.javaとList<PostInfo>をログイン情報を元に取得 
		 * mypage.htmlに遷移する
		 */
		@Transactional
		@GetMapping("/user/mypage")
		public String getMypage(Model model , @AuthenticationPrincipal LoginUserDetails user) {
			
			//ユーザーの情報を取得
			Userdata userdata =  accountService.selectProfileOne(user.getUserId());
			model.addAttribute("userdata", userdata);
			//ユーザーのポストを取得
			List<PostInfo> posts = postService.selectPostByUserId(user.getUserId());
			model.addAttribute("posts", posts);
			
			return "user/mypage";
		}
		
		
	//***************************userpage(他のユーザーのページ)に関するメソッド*************
		
		/*
		 * String型の引数userIdからPostinfoとUserdataを取得してuserdetails.htmlに遷移する
		 */
		@Transactional
		@GetMapping("/user/userpage/{userId}")
		public String getUserpage(Model model ,@PathVariable("userId") String userId ,@AuthenticationPrincipal LoginUserDetails user) {
			//ユーザー自身のアカウントの場合はマイページにアクセスする
			if(userId.equals(user.getUserId())) {
				return "redirect:/user/mypage";
			}
			
			//フォローしているかどうかの判定
			//フォローしていない場合false
			Boolean followflg = false;		
			List<Userdata> followList=followService.searchByUserId(user.getUserId());
			for(Userdata follow : followList) {
				if (follow.getUserId().equals(userId)) {
					followflg = true;
				}
			}
			model.addAttribute("followflg" ,followflg);
			
			//ユーザーの情報を取得
			Userdata userdata = accountService.selectProfileOne(userId);
			model.addAttribute("userdata" , userdata);
			//ユーザーの投稿を取得
			List<PostInfo> posts = postService.selectPostByUserId(userId);
			model.addAttribute("posts" ,posts);
			
			return "user/userdetail";
		}
		
		//ユーザーの検索フォームの取得
//		@Transactional
//		@GetMapping("/user/search")
//		public String getSearchForm(Model model ) {
//			model.addAttribute("userSearchForm", new UserSearchForm());
//			List< Userdata> userdatas= new ArrayList<>();			
//			model.addAttribute("userdatas" , userdatas);
//			return "user/search";
//		}
//		
		//ユーザー検索の結果を表示する
		@GetMapping("user/search")
		public String userSearchResult(@Validated UserSearchForm userSearchForm,
										BindingResult result ,
										Model model) {
			model.addAttribute("userSearchForm", userSearchForm);
			List<String> keywords = new ArrayList<>();
			if(userSearchForm.getKeyword() == null) {
				keywords=null;
			}else {
				keywords= userSearchForm.splitKeyword();
			}
			 List< Userdata> userdatas= accountService.searchUser(keywords);			
			model.addAttribute("userdatas" , userdatas);
			return "user/search";
		}

}
