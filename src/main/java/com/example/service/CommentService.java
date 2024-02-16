package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Comment;
import com.example.model.CommentInfo;
import com.example.repository.CommentMapper;

@Service
public class CommentService {
	
	private final CommentMapper commentMapper;
	
	public CommentService(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
	
	//コメントの投稿
	@Transactional
	public void uploadComment(Comment comment) {
		commentMapper.uploadComment(comment);
	}
	
	//postIdから検索
	@Transactional
	public List<CommentInfo> getCommentsByPostId(int postId){
		List<CommentInfo>comments = commentMapper.findByPostId(postId);
		return comments;
	}
	
	//コメントの削除
	@Transactional
	public void deleteComment(int commentId) {
		commentMapper.deleteComment(commentId);
	}
	
	@Transactional
	public void deleteCommentByUserID(String userId) {
		commentMapper.deleteCommentByUserId(userId);
	}
	
	@Transactional
	public void deleteCommentByPostId(int postId) {
		commentMapper.deleteCommentByPostId(postId);
	}


}
