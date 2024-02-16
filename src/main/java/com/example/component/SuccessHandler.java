package com.example.component;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.service.AccountService;
import com.example.service.security.LoginUserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component("SuccessHandler")
@RequiredArgsConstructor
public class SuccessHandler implements AuthenticationSuccessHandler{
	
	private final AccountService accountService;
	
//	private final UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {

		//ユーザー情報の取得
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder
									.getContext()
									.getAuthentication()
									.getPrincipal();
		
		String redirectPath = request.getContextPath();
		
		
		
		//パスワードの有効期限チェック
		if(user.getPasswordUpdateDate().isAfter(LocalDate.now())) {
			redirectPath += "/user/mypage";
			
		}else {
			
			redirectPath += "/login/passwordChange";
		}	
		accountService.updateLoginFailed(user.getUserId());
		response.sendRedirect(redirectPath);		
	}

}
