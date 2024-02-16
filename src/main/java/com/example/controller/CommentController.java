package com.example.controller;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.form.CommentForm;
import com.example.model.Comment;
import com.example.model.CommentInfo;
import com.example.model.PostInfo;
import com.example.service.CommentService;
import com.example.service.PostService;
import com.example.service.security.LoginUserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Controller
@Slf4j
public class CommentController {
	
	private final CommentService commentService;
	
	private final PostService postService;
	
//********************************************コメントの投稿********************************************
	
	/*
	 * コメントの投稿
	 * post.htmlから実行
	 * CommentFormをページからの引数にする
	 * エラーがあった際の遷移先post.html
	 * その際にmodelにPostInfoとList<CommentInfo>を追加する
	 * 
	 * 投稿が成功した場合もpost.htmlに遷移する
	 * modelにはPostInfoとList<CommentInfo>を追加する
	 */
	@Transactional
	@PostMapping("/comment/upload")
	public String uploadComment(@Validated CommentForm commentForm ,
								BindingResult result , 
								RedirectAttributes redirectAttributes, 
								Model model,
								@AuthenticationPrincipal LoginUserDetails user) {
		
		int postId = commentForm.getPostId();
 		//エラーが発生したときの処理
		if(result.hasErrors()) {

			//該当するpostを検索
			PostInfo postInfo = postService.selectPostInfoByPostId(postId);
			model.addAttribute(postInfo);
			
			//ユーザーがいいねしているかどうかの判定
			//いいねしている場合はいいね解除ボタンが表示される
			List<String>users =postInfo.getUserList();
			boolean likeflg = false;
			if(users.contains(user.getUserId())) {
				likeflg = true;
			}
			model.addAttribute("likeflg" ,likeflg);
			
			//ユーザー自身の投稿かどうか判定
			//ユーザー自身の投稿ならいいねする、いいね解除のボタンが表示されない
			boolean userself=false;
			log.info("認証情報 : "+user.getUserId());
			log.info("ポストの情報 : "+postInfo.getUserId());
			if(postInfo.getUserId().equals(user.getUserId())) {
				userself=true;
			}
			model.addAttribute("userself",userself);
			
			//commentFormをモデルにセットする。コメントの投稿に用いる。
			model.addAttribute("commentForm" , commentForm);
			
			//ポストに対するコメントを検索する。
			List<CommentInfo> comments = commentService.getCommentsByPostId(postId);
			model.addAttribute("comments" ,comments);
			
			return "post/post";	
		}
		
		//コメントの登録処理
		Comment comment = new Comment();
		comment.setAppUserName(user.getAppUserName());
		comment.setCreated(LocalDateTime.now());
		comment.setMessage(commentForm.getCommentText());
		comment.setPostId(commentForm.getPostId());
		comment.setUserId(user.getUserId());
		commentService.uploadComment(comment);
		
		//リダイレクトの処理
		redirectAttributes.addAttribute("postId" , postId);
		
		return "redirect:/post/show/{postId}";
	}
	
	
//****************************************コメントを削除する処理****************************************
	
	/**
	 * コメントを削除する処理
	 * コメントをしたユーザーのuserIdと認証情報を比較する
	 * コメント削除後の遷移先はコメントをした投稿のページ
	 * 
	 * @param comment
	 * @param user
	 * @return
	 */
	@PostMapping("/comment/delete")
	public String deletecomment(Model model,
							@RequestParam("userId") String userId,
							@RequestParam("commentId")int commentId,
							@RequestParam("postId") int postId,					
							@AuthenticationPrincipal LoginUserDetails user ,
							RedirectAttributes redirectAttributes) throws IOException{
		
		//コメントを投稿したユーザーのIDが操作しているユーザーのIDと異なるときの処理
		if(!userId.equals(user.getUserId())) {
			redirectAttributes.addAttribute("postId" , postId);
			return "redirect:/post/show/{postId}";
		}
			
		commentService.deleteComment(commentId);
		redirectAttributes.addAttribute("postId" ,  postId);
		return "redirect:/post/show/{postId}";
	}

}
