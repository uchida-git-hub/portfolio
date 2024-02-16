package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.form.SearchForm;
import com.example.model.PostCategory;
import com.example.model.PostInfo;
import com.example.model.TechCategory;
import com.example.model.Userdata;
import com.example.service.CategoryService;
import com.example.service.FollowService;
import com.example.service.PostService;
import com.example.service.security.LoginUserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Controller
@Slf4j
public class TimeLineController {
	
	private final PostService postService;
	
	private final FollowService followService;
	
	private final CategoryService categoryService;
	
//*********************************************************検索に関するメソッド************************************************	
	
	
	@GetMapping("/timeline/search")
	public String searchPost(Model model ) {
		//カテゴリを取得
		List<PostCategory> postCategories = categoryService.selectPostCategories();
		model.addAttribute("postCategories" , postCategories);
		List<TechCategory> techCategories = categoryService.selectTechCategory();
		model.addAttribute("techCategories" , techCategories);
		SearchForm searchForm = new SearchForm();
		model.addAttribute("searchForm" , searchForm);
		
		return "timeline/search";
		
	}
	
	@GetMapping("/search/result")
	public String seachPost(@Validated SearchForm searchForm
								,BindingResult result
								,Model model) {
		//エラーが発生した場合の処理
		if(result.hasErrors()) {
			List<PostCategory> postCategories = categoryService.selectPostCategories();
			model.addAttribute("postCategories" , postCategories);
			List<TechCategory> techCategories = categoryService.selectTechCategory();
			model.addAttribute("techCategories" , techCategories);
			model.addAttribute("searchForm" , searchForm);
			return "timeline/search";
		}
		
		List<PostInfo> posts = postService.seachPost(searchForm);
		model.addAttribute("posts" , posts);
		return "timeline/timeline";
		
	}

//*********************************************************タイムラインの取得***********************************************************************
	
	
	@GetMapping("/timeline/timeline")
	public String timeLine(Model model , 
					@AuthenticationPrincipal LoginUserDetails user) {
		
		String userId = user.getUserId();
		
		List<Userdata>followList = followService.searchByUserId(userId);
		List<String>followIdList = new ArrayList<>();
		for(Userdata userdata : followList) {
			followIdList.add(userdata.getUserId());
		}
		List<PostInfo> posts = postService.searchTimeLine(followIdList);
		model.addAttribute("posts" , posts);
		return "timeline/timeline";
	}
	
}
