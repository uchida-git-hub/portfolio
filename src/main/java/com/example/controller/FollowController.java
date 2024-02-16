package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.service.FollowService;
import com.example.service.security.LoginUserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class FollowController {
	
	private final FollowService followService;
	
//**************************************フォローする*****************************************************
	
	/**
	 * フォローの処理
	 * フォローしようとしているユーザーの認証情報を参照し、自分自身をフォローしようとしている場合はユーザーページにリダイレクトする。
	 * 処理の完了後はフォローしたユーザーのプロフィール画面へリダイレクトの処理
	 * @param user
	 * @param follower
	 * @param model
	 * @return
	 */
	@Transactional
	@PostMapping("/follow/{follower}")
	public String insertFollow( @PathVariable("follower") String follower ,
								RedirectAttributes redirectAttributes,
								@AuthenticationPrincipal LoginUserDetails user  
								) {
		//フォローしようとしているユーザーのIDとログインしているユーザーのIDが一致しているとき、もとのページへリダイレクトする。
		if(user.getUserId().equals(follower)) {
			redirectAttributes.addAttribute("userId",follower);
			return "redirect:/user/userpage";
		}
		
		followService.followUser(user.getUserId(), follower);
		//リダイレクトの処理
		redirectAttributes.addAttribute(("userId") , follower);
		log.info("正常終了");
		return "redirect:/user/userpage/{userId}";
	}
	
//**************************************フォローの解除****************************************************
	
	/**
	 * フォローの解除
	 * 解除しようとしているフォローが本人によるものか認証情報を参照して確認する。
	 * 本人でなければ、フォローを解除しようとしていたユーザーのユーザーページにリダイレクト
	 * ログイン情報からuserIdを取得
	 * フォローしているユーザー1人のuserIdをString型の変数folloerとして受け取る
	 * フォローの解除完了後は解除したユーザーのプロフィール（userdetails.html）にリダイレクト
	 * @param user
	 * @param follower
	 * @param model
	 * @return
	 */
	@Transactional
	@PostMapping("/follow/delete/{follower}")
	public String deleteFollow(@AuthenticationPrincipal LoginUserDetails user ,
							@PathVariable String follower ,
							RedirectAttributes redirectAttributes 
							) {
		
		//フォローの解除
		followService.deleteFollow(user.getUserId(), follower);
		redirectAttributes.addAttribute("userId",follower);
		return "redirect:/user/userpage/{userId}";
	}
	
//*********************************************フォローしているユーザーの一覧を表示**************************************************	
	
	/**
	 * 認証情報からuserIdを取得しユーザーのフォローしているアカウント一覧を返す
	 * フォローしているアカウントの一覧はUserdata型のListとして取得し、modelに追加する
	 * 遷移後の画面はfollow.html
	 * @param user
	 * @param model
	 * @return
	 */
	@Transactional
	@GetMapping("/follow/list")
	public String showFollowList(@AuthenticationPrincipal LoginUserDetails user , 
						Model model) {
		//フォローしているユーザー一覧をデータベースから取得
		model.addAttribute("userdatas" , followService.searchByUserId(user.getUserId()));

		return "user/follow";
	}

//*********************************************フォローされているユーザーの一覧を表示する***********************************************
	
	@Transactional
	@GetMapping("/follow/folloer/list")
	public String showFollowerList(@AuthenticationPrincipal LoginUserDetails user , Model model) {
		
		model.addAttribute("userdatas" , followService.seachfollower(user.getUserId()));
		return "user/follower";
	}
	

}
