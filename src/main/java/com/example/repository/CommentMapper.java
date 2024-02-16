package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.Comment;
import com.example.model.CommentInfo;



@Mapper
public interface CommentMapper {
	
	//コメントを投稿
	public void uploadComment(Comment comment);
	
	//コメントを1件削除
	public void deleteComment(int commentId);
	
	//ポストIDから検索
	public List<CommentInfo> findByPostId(int postId);
	
	//コメントを削除(ユーザーの退会時に使用)
	public void deleteCommentByUserId(String userId);
	
	//ポストの削除時にコメントも削除する
	public void deleteCommentByPostId(int postId);
}
