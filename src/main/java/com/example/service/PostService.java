package com.example.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.form.PostUpdateForm;
import com.example.form.SearchForm;
import com.example.model.Post;
import com.example.model.PostInfo;
import com.example.repository.CategoryMapper;
import com.example.repository.LikeMapper;
import com.example.repository.PostMapper;

@Service 
public class PostService {
	
	private final PostMapper postMapper;
	
	private final CategoryMapper categoryMapper;
	
	private final LikeMapper likeMapper;
	
	public PostService(PostMapper postMapper , CategoryMapper categoryMapper ,LikeMapper likeMapper) {
		this.postMapper = postMapper;
		this.categoryMapper = categoryMapper;
		this.likeMapper = likeMapper; 
	}
	
	@Transactional
	public void deletePostByUserId(String userId) {
		postMapper.deletePostByUserId(userId);
	}
	
	/**
	 * ポストの投稿
	 * @param post
	 */
	@Transactional
	public void uploadPost(String userId , String message , LocalDateTime created, 
							int postCategoryId , int techCategoryId) {
		Post post = new Post();
		post.setUserId(userId);
		post.setMessage(message);
		post.setCreated(created);
		postMapper.uploadPost(post);
		
		categoryMapper.uploadPostCategory(postCategoryId, post.getPostId());
		categoryMapper.uploadTechCategory(techCategoryId, post.getPostId());
		likeMapper.insertLike(post.getPostId(), userId);
		
	}
	
	/**ポストの削除
	 * ポストに対するコメントと、各カテゴリとの中間テーブルも削除する
	 * @param postId
	 */
	@Transactional
	public void deletePost(int postId) {
		postMapper.deletePostByPostId(postId);
	}
	
	/**
	 * ポストの検索
	 * カテゴリ、キーワードから該当するポストを取得する
	 * @param techCategory
	 * @param postCategory
	 * @param keyword
	 * @return
	 */
	@Transactional
	public List<PostInfo> seachPost(SearchForm searchForm){
		//キーワードの編集
		//全角スペースを半角スペースに置換する
		//2つ以上連続するスペースを１つの半角スペースに置換する
		List<String> keywordsList = new ArrayList<>();
		if(searchForm.getKeyword() != null) {
			String keyword = searchForm.getKeyword().replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
			//キーワードを半角スペースで区切って配列に変換
			String[] keywords = keyword.split(" ");
			//配列をリストに変換する
			for(String word : keywords) {
				keywordsList.add(word);
			}
		}
		//フォームからカテゴリIDを取得
		int techCategoryId = searchForm.getTechCategoryId();
		int postCategoryId = searchForm.getPostCategoryId();
		
		List<PostInfo> posts = postMapper.searchPost(techCategoryId, postCategoryId, keywordsList);
		return posts;
	}
	
	/**
	 * フォローしているユーザーのポストを取得する
	 * @param follows
	 * @return
	 */
	@Transactional
	public List<PostInfo> searchTimeLine(List<String> follows){
		List<PostInfo> timeline = postMapper.selectTimeLine(follows);
		return timeline;
	}
	
	/**
	 * ユーザーIDからポストを検索する
	 * 戻り値はList<PostInfo>になる
	 * @param userId
	 * @return
	 */
	@Transactional
	public List<PostInfo> selectPostByUserId(String userId){
		List<PostInfo> posts = postMapper.selectAllPostByUserIdOrderByNew(userId);
		return posts;
	}
	
	//postIdから検索する
	@Transactional
	public PostInfo selectPostInfoByPostId(int postId) {
		PostInfo postInfo = postMapper.selectPostInfoByPostId(postId);
		return postInfo;
	}
	
	//postの更新
	@Transactional
	public void updatePost(PostUpdateForm postUpdateForm) {
		//ポストの更新
		postMapper.updatePost(postUpdateForm.getPostId(), postUpdateForm.getMessage());
		//カテゴリの更新
		categoryMapper.updatePostCategory(postUpdateForm.getPostId(), postUpdateForm.getPostCategoryId());
		categoryMapper.updateTechCategory(postUpdateForm.getPostId(), postUpdateForm.getTechCategoryId());
	}
	
}
