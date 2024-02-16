package com.example.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
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

import com.example.form.CommentForm;
import com.example.form.PostForm;
import com.example.form.PostUpdateForm;
import com.example.model.CommentInfo;
import com.example.model.PostCategory;
import com.example.model.PostInfo;
import com.example.model.TechCategory;
import com.example.service.CategoryService;
import com.example.service.CommentService;
import com.example.service.LikeService;
import com.example.service.PostService;
import com.example.service.security.LoginUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class PostController {
	
	private final LikeService likeService;
	
	private final PostService postService;
	
	private final CommentService commentService;
	
	private final CategoryService categoryService;
	
//********************************************:ポストの投稿に関するメソッド***************************************
	
	/**
	 * ポスト投稿用のページに遷移
	 * PostForm.javaのインスタンスを渡す
	 * 遷移後の画面はpost.html
	 * @param model
	 * @return
	 */
	@GetMapping("/post/upload")
	public String getPostForm(Model model) {
		PostForm postForm = new PostForm();
		model.addAttribute("postForm" ,postForm);
		//カテゴリをmodelに追加する
		List<PostCategory> postCategories = categoryService.selectPostCategories();
		model.addAttribute("postCategories",postCategories);
		List<TechCategory> techCategories = categoryService.selectTechCategory();
		model.addAttribute("techCategories" , techCategories);
		
		return "post/upload";
	}
	
	/**
	 * postの投稿
	 * ポストフォームの情報を元にpostをデータベースに登録する
	 * Createdは現在時刻を使用
	 * userIdは認証情報を元に登録する
	 * エラーが発生した場合の遷移先はpost.html
	 * 正常終了後の遷移画面はmypage.html
	 * @param postForm
	 * @param result
	 * @param user
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@PostMapping("/post/upload")
	public String uploadPost(@Validated PostForm postForm,
							BindingResult result , 
							@AuthenticationPrincipal LoginUserDetails user ,
							RedirectAttributes redirectAttributes,
							Model model) {
		
		if(result.hasErrors()) {
			//カテゴリをmodelに追加する
			List<PostCategory> postCategories = categoryService.selectPostCategories();
			model.addAttribute("postCategories",postCategories);
			List<TechCategory> techCategories = categoryService.selectTechCategory();
			model.addAttribute("techCategories" , techCategories);
			model.addAttribute("postForm" ,postForm);
			return "post/upload";
		}
		//ポストをデータベースに登録
		String userId = user.getUserId() ;
		String message = postForm.getPostText();
		LocalDateTime created = LocalDateTime.now();
		int postCategoryId = postForm.getPostCategoryId();
		
		int techcategoryId = postForm.getTechCategoryId();
		Collection< ? extends GrantedAuthority> role = user.getAuthorities();
		
		postService.uploadPost(userId , message , created ,postCategoryId , techcategoryId);
		//登録完了後はリダイレクトする
		return "redirect:/user/mypage";
	}
	
	
//************************************************ポストの削除に関する処理*****************************************************
	
	/**
	 * 投稿の削除
	 * 削除しようとしている投稿のuserIdと認証情報のuserIdが異なる場合は削除できない
	 * 
	 * @param postId
	 * @param userId
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@Transactional
	@PostMapping("/post/delete/{postId}/{userId}")
	public String deletePost(@PathVariable("postId") int postId,
							@PathVariable("userId") String userId, 
							@AuthenticationPrincipal LoginUserDetails user,
							RedirectAttributes redirectAttributes) {
		//ポストを投稿したユーザーのIDと操作しているユーザーのIDが一致しているか確認
		if(!userId.equals(user.getUserId())) {
			log.info("post.userId : "+userId);
			log.info("authentiocation.userId : "+user.getUserId());
			log.info("ユーザー名と認証情報が違います");
			return "redirect:/user/mypage";
		}
		//ポストの削除
		postService.deletePost(postId);
		categoryService.deleteCategoryByPostId(postId);
		commentService.deleteCommentByPostId(postId);
		likeService.deleteLikeByPostId(postId);
		
		//リダイレクトの処理
		return "redirect:/user/mypage";
	}
	
	
//**************************************ポストの詳細を表示する（コメントを表示）************************************************
	
	/**
	 * postIdからポストの詳細画面を取得する
	 * ポストの詳細画面にはpostに対するコメントを表示する
	 * 詳細画面にはポストに対し新たにコメントを投稿するフォームがある
	 * modelにPostInfo , CommentInfo , CommentFormを追加する
	 * 遷移する画面はpost.html
	 * 
	 * @param model
	 * @param postInfo
	 * @return
	 */
	@GetMapping("/post/show/{postId}")
	public String showPost(@PathVariable int postId,
							Model model , 
							@AuthenticationPrincipal LoginUserDetails user) {
		
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
		if(postInfo.getUserId().equals(user.getUserId())) {
			userself=true;
		}
		model.addAttribute("userself",userself);
		
		//commentFormをモデルにセットする。コメントの投稿に用いる。
		CommentForm commentForm = new CommentForm();
		model.addAttribute("commentForm" , commentForm);
		
		//ポストに対するコメントを検索する。
		List<CommentInfo> comments = commentService.getCommentsByPostId(postId);
		model.addAttribute("comments" ,comments);
		
		return "post/post";	
	}
	
//===================================ポストの更新====================================================================================
	
	/**
	 * 投稿の更新画面を取得
	 * 認証情報のuserIdと投稿のuserIdが異なる場合はページを表示せず、投稿の詳細画面に戻る。
	 * @param postInfo
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/post/update")
	public String getUpdateForm(PostInfo postInfo,
								Model model , 
								@AuthenticationPrincipal LoginUserDetails user) {
		//認証情報のuserIdとpostInfoのuserIdを比較
		if( !postInfo.getUserId().equals(user.getUserId())) {
			return "/post/show/{postId}";
		}
		
		//postFormの取得
		PostUpdateForm postUpdateForm = new PostUpdateForm();
		postUpdateForm.setMessage(postInfo.getMessage());
		postUpdateForm.setPostId(postInfo.getPostId());
		postUpdateForm.setUserId(user.getUserId());
		postUpdateForm.setPostCategoryId(0);
		postUpdateForm.setTechCategoryId(0);
		model.addAttribute("postUpdateForm" , postUpdateForm);
		//カテゴリ一覧の取得
		List<PostCategory>postCategories= categoryService.selectPostCategories();
		model.addAttribute( "postCategories", postCategories);
		List<TechCategory>techCategories= categoryService.selectTechCategory();
		model.addAttribute("techCategories" , techCategories);
		
		return "post/update_post";
	}
	
	/**
	 * 投稿の編集の実行
	 * FormのuserIdと認証情報のuserIdが一致しない場合は実行させない
	 * 
	 * @param redirectAttributes
	 * @param postUpdateForm
	 * @param user
	 * @return
	 */
	@Transactional
	@PostMapping("/post/update")
	public String postUpdateForm(RedirectAttributes redirectAttributes , 
								@Validated PostUpdateForm postUpdateForm , 
								BindingResult result,
								Model model,
								@AuthenticationPrincipal LoginUserDetails user) {
		//認証情報のとformのuserIdの比較
		if(!postUpdateForm.getUserId().equals(user.getUserId())) {
			redirectAttributes.addAttribute("postId" , postUpdateForm.getPostId());
			return "redirect:/post/show/{postId}";
		}
		
		if(result.hasErrors()) {
			log.info("入力内容にエラー");
			model.addAttribute(postUpdateForm);
			List<PostCategory>postCategories= categoryService.selectPostCategories();
			model.addAttribute( "postCategories", postCategories);
			List<TechCategory>techCategories= categoryService.selectTechCategory();
			model.addAttribute("techCategories" , techCategories);
			return "post/update_post";
		}
		
		//ポストの更新
		postService.updatePost(postUpdateForm);
		redirectAttributes.addAttribute("postId" , postUpdateForm.getPostId());
		
		return "redirect:/post/show/{postId}";
		
	}
	
	
}
