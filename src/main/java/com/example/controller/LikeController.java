package com.example.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.PostInfo;
import com.example.service.LikeService;
import com.example.service.security.LoginUserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Controller
@Slf4j
public class LikeController {
	
	private final LikeService likeService;

//*******************************************いいねする******************************************	
	
	/**
	 * @param postId
	 * @param userId
	 * @param user
	 * @param redirectAttributes
	 * @return
	 * 
	 * 投稿にいいねする処理。自分の投稿にはいいねできないようにする。
	 * 追加後はいいねした投稿の画面へリダイレクトする
	 */
	@Transactional
	@PostMapping("/like/add/{postId}/{userId}")
	public String like(	@PathVariable int postId , 
						@PathVariable String userId,
						@AuthenticationPrincipal LoginUserDetails user,
						RedirectAttributes redirectAttributes
						) {
		//自分の投稿にいいねしようとしたときの処理
		if(userId.equals(user.getUserId())) {
			redirectAttributes.addAttribute("postId" , postId);
			return "redirect:/post/show/{postId}";
		}

		//いいねを追加
		likeService.insertLike(postId ,user.getUserId());
		
		//リダイレクトの処理
		redirectAttributes.addAttribute("postId" , postId);
			
		return "redirect:/post/show/{postId}";
		
	}
	
//***************************************いいねの取り消し*****************************************
	
	/**
	 * 
	 * @param postId
	 * @param userId
	 * @param user
	 * @param redirectAttributes
	 * @return
	 * 
	 * いいねの取り消し
	 * 取り消そうとしているいいねのuserIdと認証情報のuserIdが一致するか確認する。
	 * 一致しない場合は取り消そうとしている投稿の画面にリダイレクトする
	 * 取り消しが正常終了した場合はいいねを取り消した投稿の詳細画面にリダイレクトする
	 */
	@PostMapping("/like/delete/{postId}/{userId}")
	public String deletelike(@PathVariable int postId , 
							@PathVariable String userId,
							@AuthenticationPrincipal LoginUserDetails user ,
							RedirectAttributes redirectAttributes) {
		//取り消そうとしているいいねのuserIdと認証情報のuserIdが異なる場合の処理
		if(userId.equals(user.getUserId())) {
			redirectAttributes.addAttribute("postId" , postId);
			return "redirect:/post/show/{postId}";
		}
		//いいねの取り消し
		likeService.deleteLike(postId, user.getUserId());
		//リダイレクトの処理
		redirectAttributes.addAttribute("postId" , postId);
		return "redirect:/post/show/{postId}";
	}

//****************************************いいね一覧取得******************************************
	/**
	 * 
	 * @param user
	 * @param model
	 * @return
	 * データベースからいいねした投稿の一覧を取得する。
	 */
	@GetMapping("/like/list")
	public String likeList(@AuthenticationPrincipal LoginUserDetails user ,Model model) {
		//データベースからいいねしている投稿の一覧を取得
		List<PostInfo>posts = likeService.selectPostInfoByUserId(user.getUserId());
		Iterator<PostInfo> it = posts.iterator();
		//自分自身の投稿が含まれている場合はその投稿を除外する
		while (it.hasNext()) {
			PostInfo postInfo = (PostInfo) it.next();
			if(postInfo.getUserId().equals(user.getUserId())) {
				it.remove();
			}
		}
		
		model.addAttribute("posts", posts);
		return "timeline/like";
	}
	
	
}
