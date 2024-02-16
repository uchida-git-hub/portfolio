package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.PostInfo;
import com.example.model.Userdata;
import com.example.service.AccountService;
import com.example.service.PostService;
import com.example.service.security.LoginUserDetails;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminController {
	
	private final AccountService accountService;
	
	private final PostService postService;
	
	@Transactional
	@GetMapping("/admin/adminpage")
	public String getAdminPage(Model model, @AuthenticationPrincipal LoginUserDetails user) {
		
		return "admin/admin_page";
	}
	
	//ロックされているユーザーの取得
	@Transactional
	@GetMapping("/admin/lockList")
	public String getLockList(Model model ,@AuthenticationPrincipal LoginUserDetails user) {
		List<Userdata> users = accountService.getLockList();
		model.addAttribute("userdatas" , users);
		return "admin/lock_list";
	}
	
	//ロックの解除
	@Transactional
	@PostMapping("/admin/unlock/{userId}")
	public String unlockUser( @PathVariable String userId , Model model) {
		accountService.unlock(userId);
		return "redirect:/admin/lockList"; 
	}
	
	//凍結しているユーザーの一覧取得
	@Transactional
	@GetMapping("/admin/enableList")
	public String getEnableList(Model model) {
		List<Userdata> users = accountService.getEnableList();
		model.addAttribute("userdatas" , users);
		return "admin/enabled_list";
	}
	
	//凍結の解除
	@Transactional
	@PostMapping("/admin/enabled/{userId}")
	public String unEnable(@PathVariable String userId , Model model) {
		accountService.unenable(userId);
		return "redirect:/admin/enableList";
	}
	
	//アカウントを凍結する
	@Transactional
	@PostMapping("/admin/enable/{userId}")
	public String enable(@PathVariable String userId , Model model) {
		accountService.enable(userId);
		return "redirect:/admin/enableList";
	}
	
	//
	@Transactional
	@PostMapping("/admin/delete/post/{postId}")
	public String adminDeletePost(@PathVariable int postId, Model model) {
		postService.deletePost(postId);
		return "redirect:/admin/adminpage";
	}
	
	//管理者としてプロフィールを取得する
	@Transactional
	@GetMapping("/admin/userpage/{userId}")
	public String getUserpage(Model model ,@PathVariable("userId") String userId ,@AuthenticationPrincipal LoginUserDetails user) {
		//ユーザー自身のアカウントの場合はマイページにアクセスする
		if(userId.equals(user.getUserId())) {
			return "redirect:/user/mypage";
		}
		
		//ユーザーの情報を取得
		Userdata userdata = accountService.selectUserOneByAdomin(userId);
		model.addAttribute("userdata" , userdata);
		//ユーザーの投稿を取得
		List<PostInfo> posts = postService.selectPostByUserId(userId);
		model.addAttribute("posts" ,posts);
		
		return "admin/admin_profile";
	}

}
